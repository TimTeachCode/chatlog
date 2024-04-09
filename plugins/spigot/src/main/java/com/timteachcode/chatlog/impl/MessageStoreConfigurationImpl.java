package com.timteachcode.chatlog.impl;

import com.timteachcode.chatlog.store.IMessageStoreConfiguration;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.concurrent.TimeUnit;

public class MessageStoreConfigurationImpl implements IMessageStoreConfiguration {
    private final long expirationTime;
    private final int maxMessages;

    public MessageStoreConfigurationImpl(FileConfiguration config) {
        expirationTime = TimeUnit.MINUTES.toMillis(config.getInt("message_store.expiration", 15));
        maxMessages = config.getInt("message_store.max", 50);
    }

    @Override
    public long expirationTime() {
        return expirationTime;
    }

    @Override
    public int maxMessages() {
        return maxMessages;
    }
}
