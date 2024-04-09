package com.timteachcode.chatlog.impl;

import com.timteachcode.chatlog.database.IDatabaseCredentials;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;

@RequiredArgsConstructor
public class DatabaseCredentialsImpl implements IDatabaseCredentials {
    private final FileConfiguration config;

    @Override
    public String hostname() {
        return config.getString("db_credentials.hostname");
    }

    @Override
    public int port() {
        return config.getInt("db_credentials.port");
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
