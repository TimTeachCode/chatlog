package com.timteachcode.chatlog.database;

public interface IDatabaseCredentials {
    String hostname();
    int port();
    String database();
    String username();
    String password();

    default String getConnectionUrl() {
        return String.format("jdbc:mysql://%s:%s@%s:%d/%s", username(), password(), hostname(), port(), database());
    }
}
