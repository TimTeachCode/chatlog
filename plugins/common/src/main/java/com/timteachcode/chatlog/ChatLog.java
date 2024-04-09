package com.timteachcode.chatlog;

import com.timteachcode.chatlog.database.DatabaseConnection;
import com.timteachcode.chatlog.database.IDatabaseCredentials;
import com.timteachcode.chatlog.store.IMessageStoreConfiguration;
import com.timteachcode.chatlog.store.MessageData;
import com.timteachcode.chatlog.store.MessageStore;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ChatLog {
    private final ILogHandler logHandler;
    private final IScheduler scheduler;
    private DatabaseConnection databaseConnection;
    @Getter private MessageStore messageStore;

    public Runnable createMessageStore(IMessageStoreConfiguration config) {
        messageStore = new MessageStore(config);
        return messageStore.scheduleTask();
    }

    public void shutdown() {
        databaseConnection.shutdown();
    }

    public void createChatLog(IChatter reported, IChatter reporter, Consumer<CreateChatLogReturn> onCreated) {
        if (!messageStore.hasMessageFrom(reported.getUniqueId())) {
            onCreated.accept(CreateChatLogReturn.fail(CreateChatLogReturn.ErrorType.NO_MESSAGE_FOUND));
            return;
        }

        List<MessageData> messages = messageStore.lastMessages();

        scheduler.runAsync(() -> {
            Connection connection = null;
            try {
                connection = databaseConnection.openConnection();

                String id = Integer.toHexString(UUID.randomUUID().hashCode());
                insertChatLog(connection, id, reported.getName(), reported.getUniqueId(), reporter.getName(), reporter.getUniqueId(), messages);
                onCreated.accept(CreateChatLogReturn.success(id));
            } catch (Exception e) {
                logHandler.error("Error while creating chatlog", e);
                onCreated.accept(CreateChatLogReturn.fail(CreateChatLogReturn.ErrorType.EXCEPTION));
            } finally {
                if (connection != null) {
                    databaseConnection.closeConnection(connection);
                }
            }
        });
    }

    private ChatLog(ILogHandler logHandler, IScheduler scheduler) {
        this.logHandler = logHandler;
        this.scheduler = scheduler;
    }

    private void setupDatabaseConnection(IDatabaseCredentials databaseCredentials) {
        databaseConnection = DatabaseConnection.createConnection(databaseCredentials, logHandler);
    }

    private void insertChatLog(
            Connection connection,
            String id,
            String reportedName,
            UUID reportedUniqueId,
            String reporterName,
            UUID reporterUniqueId,
            List<MessageData> messages) throws SQLException {
        PreparedStatement chatLogStatement = connection.prepareStatement("INSERT INTO `chatlog` (`id`, `reportedUniqueId`, `reportedName`, `reporterUniqueId`, `reporterName`) VALUES (?, ?, ?, ?, ?);");
        chatLogStatement.setString(1, id);
        chatLogStatement.setString(2, reportedUniqueId.toString());
        chatLogStatement.setString(3, reportedName);
        chatLogStatement.setString(4, reporterUniqueId.toString());
        chatLogStatement.setString(5, reporterName);
        chatLogStatement.executeUpdate();

        PreparedStatement messageStatement = connection.prepareStatement("INSERT INTO `message` (`issuer`, `uuid`, `name`, `message`, `time`, `reported`, `reporter`, `chatLogId`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
        for (MessageData message : messages) {
            messageStatement.setString(1, message.getIssuer().name());
            messageStatement.setString(2, message.getIssuer().equals(MessageData.MessageIssuer.USER) ? message.getUniqueId().toString() : null);
            messageStatement.setString(3, message.getName());
            messageStatement.setString(4, message.getMessage());
            messageStatement.setTimestamp(5, new Timestamp(message.getTimestamp()));
            messageStatement.setBoolean(6, reportedUniqueId.equals(message.getUniqueId()));
            messageStatement.setBoolean(7, reporterUniqueId.equals(message.getUniqueId()));
            messageStatement.setString(8, id);
            messageStatement.addBatch();
        }
        messageStatement.executeBatch();
    }

    public static ChatLog init(ILogHandler logHandler, IScheduler scheduler, IDatabaseCredentials databaseCredentials) {
        ChatLog chatLog = new ChatLog(logHandler, scheduler);
        chatLog.setupDatabaseConnection(databaseCredentials);

        return chatLog;
    }
}
