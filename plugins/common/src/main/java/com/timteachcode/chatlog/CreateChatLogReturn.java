package com.timteachcode.chatlog;

import lombok.Getter;

@Getter
public class CreateChatLogReturn {
    private final boolean successful;
    private final String id;
    private final ErrorType errorType;

    private CreateChatLogReturn(boolean successful, String id, ErrorType errorType) {
        this.successful = successful;
        this.id = id;
        this.errorType = errorType;
    }

    public static CreateChatLogReturn fail(ErrorType errorType) {
        return new CreateChatLogReturn(false, null, errorType);
    }

    public static CreateChatLogReturn success(String id) {
        return new CreateChatLogReturn(true, id, null);
    }

    public enum ErrorType {
        EXCEPTION,
        NO_MESSAGE_FOUND
    }
}
