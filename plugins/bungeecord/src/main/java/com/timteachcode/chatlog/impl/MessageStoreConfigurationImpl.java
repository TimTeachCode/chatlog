package com.timteachcode.chatlog.impl;

import com.timteachcode.chatlog.store.IMessageStoreConfiguration;
import net.md_5.bungee.config.Configuration;

import java.util.concurrent.TimeUnit;

public class MessageStoreConfigurationImpl implements IMessageStoreConfiguration {
    private final long expirationTime;
    private final int maxMessages;

    public MessageStoreConfigurationImpl(Configuration config) {
        expirationTime = TimeUnit.MINUTES.toMillis(config.getLong("message_store.expiration"));
        maxMessages = config.getInt("message_store.max");
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
