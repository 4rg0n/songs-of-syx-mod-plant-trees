package com.github.argon.sos.planttree.log;

import lombok.Getter;
import snake2d.LOG;

import java.util.HashMap;
import java.util.Map;

/**
 * For registering and receiving Loggers.
 */
public class Loggers {
    private final static Map<String, Logger> loggers = new HashMap<>();

    public final static Level LOG_LEVEL_DEFAULT = Level.INFO;

    @Getter
    private static Level rootLevel = LOG_LEVEL_DEFAULT;

    /**
     * Returns a registered Logger. Or registers it when not already present.
     */
    public static Logger getLogger(Class<?> clazz) {
        if (!loggers.containsKey(clazz.getName())) {
            loggers.put(clazz.getName(), new Logger(clazz));
        }

        return loggers.get(clazz.getName());
    }

    /**
     * Sets the log level of all registered loggers
     */
    public static void setLevels(Level level) {
        if (level.equals(rootLevel)) {
            return;
        }

        LOG.ln("Setting loggers root level: " + level);
        rootLevel = level;
        loggers.forEach((name, logger) -> logger.setLevel(level));
    }

}