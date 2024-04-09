package com.timteachcode.chatlog;

public interface ILogHandler {
    void info(String message);
    void warning(String message);
    void error(String message, Exception e);
}
