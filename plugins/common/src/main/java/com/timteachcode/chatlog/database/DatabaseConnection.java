package com.timteachcode.chatlog.database;

import com.timteachcode.chatlog.ILogHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class DatabaseConnection {
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    private final ILogHandler logHandler;
    private final String connectionUrl;
    private final AtomicBoolean shutdown = new AtomicBoolean(false);
    private final Set<Connection> openConnections = new HashSet<>();

    private DatabaseConnection(IDatabaseCredentials credentials, ILogHandler logHandler) {
        this.logHandler = logHandler;

        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            logHandler.error("JDBC Driver not found...", e);
        }

        connectionUrl = credentials.getConnectionUrl();
    }

    public Connection openConnection() {
        if (shutdown.get()) return null;

        try {
            Connection connection = DriverManager.getConnection(connectionUrl);
            openConnections.add(connection);
            return connection;
        } catch (SQLException e) {
            logHandler.error("Cannot open connections to database", e);
            return null;
        }
    }

    public void closeConnection(Connection connection) {
        if (shutdown.get()) return;

        try {
            connection.close();
            openConnections.remove(connection);
        } catch (SQLException e) {
            logHandler.error("Cannot close connection", e);
        }
    }

    public void shutdown() {
        shutdown.set(true);
        try {
            for (Connection connection : openConnections) {
                connection.close();
            }
            openConnections.clear();
        } catch (SQLException e) {
            logHandler.error("Cannot close connections", e);
        }
    }

    private static DatabaseConnection databaseConnection = null;

    public static DatabaseConnection createConnection(IDatabaseCredentials credentials, ILogHandler logHandler) {
        if (databaseConnection == null) {
            databaseConnection = new DatabaseConnection(credentials, logHandler);
        }

        return databaseConnection;
    }
}
