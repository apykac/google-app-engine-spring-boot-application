package com.roundrobin_assignment.ticketpipeline.util.log;

import java.util.function.Supplier;

public class DefaultImpl implements Logger {
    private final org.slf4j.Logger logger;

    public DefaultImpl(org.slf4j.Logger logger) {
        this.logger = logger;
    }

    @Override
    public void trace(String message, Supplier<?>... suppliers) {
        if (logger.isTraceEnabled()) {
            Object[] objects = suppliersToObject(suppliers);
            if (objects == null) {
                logger.trace(message);
            } else {
                logger.trace(message, objects);
            }
        }
    }

    @Override
    public void warn(String message, Supplier<?>... suppliers) {
        if (logger.isWarnEnabled()) {
            Object[] objects = suppliersToObject(suppliers);
            if (objects == null) {
                logger.warn(message);
            } else {
                logger.warn(message, objects);
            }
        }
    }

    @Override
    public void info(String message, Supplier<?>... suppliers) {
        if (logger.isInfoEnabled()) {
            Object[] objects = suppliersToObject(suppliers);
            if (objects == null) {
                logger.info(message);
            } else {
                logger.info(message, objects);
            }
        }
    }

    @Override
    public void debug(String message, Supplier<?>... suppliers) {
        if (logger.isDebugEnabled()) {
            Object[] objects = suppliersToObject(suppliers);
            if (objects == null) {
                logger.debug(message);
            } else {
                logger.debug(message, objects);
            }
        }
    }

    @Override
    public void error(String message, Supplier<?>... suppliers) {
        if (logger.isErrorEnabled()) {
            Object[] objects = suppliersToObject(suppliers);
            if (objects == null) {
                logger.error(message);
            } else {
                logger.error(message, objects);
            }
        }
    }

    private Object[] suppliersToObject(Supplier<?>[] suppliers) {
        if (suppliers == null || suppliers.length == 0) {
            return null;
        }
        Object[] objects = new Object[suppliers.length];
        int index = 0;
        for (Supplier<?> supplier : suppliers) {
            objects[index++] = supplier.get();
        }
        return objects;
    }
}
