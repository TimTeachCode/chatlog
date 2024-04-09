package com.timteachcode.chatlog.impl;

import com.timteachcode.chatlog.ILogHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

@RequiredArgsConstructor
public class LogHandlerImpl implements ILogHandler {
    private final Logger logger;

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warning(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message, Exception e) {
        logger.error(message, e);
    }
}
