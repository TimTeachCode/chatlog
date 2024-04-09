package com.timteachcode.chatlog.store;

public interface IMessageStoreConfiguration {
    long expirationTime();
    int maxMessages();
}
