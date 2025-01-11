package org.framework.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    // Enum for log levels
    public enum LogLevel {
        DEBUG, INFO, WARN, ERROR
    }

    private LogLevel currentLevel;  // Current log level
    private PrintWriter writer;     // Writer for logging to a file
    private boolean logToFile;      // Whether to log to a file


    private static Logger instance;
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger("", LogLevel.DEBUG, false);
        }
        return instance;
    }

    // Constructor
    public Logger(String logFileName, LogLevel level, boolean logToFile) {
        this.currentLevel = level;
        this.logToFile = logToFile;
        try {
            if (logToFile) {
                writer = new PrintWriter(new FileWriter(logFileName, true), true);
            }
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    // Log a message
    public void log(LogLevel level, String message) {
        if (level.ordinal() >= currentLevel.ordinal()) {
            String logMessage = formatMessage(level, message);
            System.out.println(logMessage);
            if (logToFile && writer != null) {
                writer.println(logMessage);
            }
        }
    }

    // Convenience methods for different log levels
    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    // Format the log message
    private String formatMessage(LogLevel level, String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return String.format("[%s] [%s] %s", timestamp, level, message);
    }

    // Close the logger
    public void close() {
        if (logToFile && writer != null) {
            writer.close();
        }
    }
}