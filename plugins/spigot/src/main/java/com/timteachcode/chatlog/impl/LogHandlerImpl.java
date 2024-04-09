package com.timteachcode.chatlog.impl;

import com.timteachcode.chatlog.ILogHandler;
import lombok.RequiredArgsConstructor;

import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class LogHandlerImpl implements ILogHandler {
    private final Logger logger;

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warning(String message) {
        logger.warning(message);
    }

    @Override
    public void error(String message, Exception e) {
        logger.log(Level.SEVERE, message, e);
    }
}
