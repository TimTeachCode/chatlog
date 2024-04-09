package com.timteachcode.chatlog;

import com.google.inject.Inject;
import com.moandjiezana.toml.Toml;
import com.timteachcode.chatlog.impl.DatabaseCredentialsImpl;
import com.timteachcode.chatlog.impl.LogHandlerImpl;
import com.timteachcode.chatlog.impl.MessageStoreConfigurationImpl;
import com.timteachcode.chatlog.impl.SchedulerImpl;
import com.timteachcode.chatlog.listener.PlayerChatListener;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Plugin(
        id="chatlog-velocity",
        name="ChatLog - Velocity",
        version = "1.0-SNAPSHOT",
        description = "ChatLog Plugin for Velocity",
        authors = { "TimTeachCode" }
)
public class ChatLogPlugin {
    @Getter private final ProxyServer server;
    private final Logger logger;
    private final Toml config;
    private final ChatLog chatLog;

    @Inject
    public ChatLogPlugin(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;

        File directory = dataDirectory.toFile();
        if (!directory.exists() && !directory.mkdir()) {
            logger.error("Could not create data directory");
        }
        File configFile = new File(directory, "config.toml");
        if (!configFile.exists()) copyConfig(configFile.toPath());
        config = new Toml().read(configFile);

        LogHandlerImpl logHandler = new LogHandlerImpl(logger);
        SchedulerImpl scheduler = new SchedulerImpl(this);
        DatabaseCredentialsImpl databaseCredentials = new DatabaseCredentialsImpl(config);

        chatLog = ChatLog.init(logHandler, scheduler, databaseCredentials);
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent ignored) {
        MessageStoreConfigurationImpl messageStoreConfiguration = new MessageStoreConfigurationImpl(config);
        server.getScheduler().buildTask(this, chatLog.createMessageStore(messageStoreConfiguration))
                .delay(1L, TimeUnit.MINUTES)
                .repeat(1L, TimeUnit.MINUTES)
                .schedule();

        server.getEventManager().register(this, new PlayerChatListener(chatLog.getMessageStore()));

        CommandMeta commandMeta = server.getCommandManager().metaBuilder("chatlog").plugin(this).aliases("cl").build();
        server.getCommandManager().register(commandMeta, new ChatLogCommand(server, chatLog, config.getString("url")));
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent ignored) {
        chatLog.shutdown();
    }

    private void copyConfig(Path path) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.toml")) {
            Files.copy(Objects.requireNonNull(inputStream), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error("Error while copying config file", e);
        }
    }
}
