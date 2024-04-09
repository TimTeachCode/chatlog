package com.timteachcode.chatlog;

public interface IScheduler {
    void runAsync(Runnable runnable);
}
