package com.timteachcode.chatlog.store;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class MessageData {
    private final MessageIssuer issuer;
    private final UUID uniqueId;
    private final String name;
    private final String message;
    private final long timestamp;

    public boolean isExpired(long time) {
        return timestamp + time < System.currentTimeMillis();
    }

    public enum MessageIssuer {
        SYSTEM,
        USER
    }
}
