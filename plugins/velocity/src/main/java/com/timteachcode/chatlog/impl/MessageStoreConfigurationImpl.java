package com.timteachcode.chatlog.impl;

import com.moandjiezana.toml.Toml;
import com.timteachcode.chatlog.store.IMessageStoreConfiguration;

import java.util.concurrent.TimeUnit;

public class MessageStoreConfigurationImpl implements IMessageStoreConfiguration {
    private final long expirationTime;
    private final int maxMessages;

    public MessageStoreConfigurationImpl(Toml config) {
        expirationTime = TimeUnit.MINUTES.toMillis(config.getLong("message_store.expiration", 15L));
        maxMessages = Math.toIntExact(config.getLong("message_store.max"));
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
