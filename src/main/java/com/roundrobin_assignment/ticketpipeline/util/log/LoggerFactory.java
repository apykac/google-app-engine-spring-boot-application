package com.roundrobin_assignment.ticketpipeline.util.log;

public class LoggerFactory {
    private LoggerFactory() {
    }

    public static Logger getLogger(Class<?> clazz) {
        return new DefaultImpl(org.slf4j.LoggerFactory.getLogger(clazz));
    }

    public static Logger getLogger(String loggerName) {
        return new DefaultImpl(org.slf4j.LoggerFactory.getLogger(loggerName));
    }
}
