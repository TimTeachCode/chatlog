package com.timteachcode.chatlog.impl;

import com.moandjiezana.toml.Toml;
import com.timteachcode.chatlog.database.IDatabaseCredentials;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DatabaseCredentialsImpl implements IDatabaseCredentials {
    private final Toml config;

    @Override
    public String hostname() {
        return config.getString("db_credentials.hostname");
    }

    @Override
    public int port() {
        return Math.toIntExact(config.getLong("db_credentials.port"));
    }

    @Override
    public String database() {
        return config.getString("db_credentials.database");
    }

    @Override
    public String username() {
        return config.getString("db_credentials.username");
    }

    @Override
    public String password() {
        return config.getString("db_credentials.password");
    }
}
