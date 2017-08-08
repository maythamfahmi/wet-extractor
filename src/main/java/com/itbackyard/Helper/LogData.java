package com.itbackyard.Helper;

import com.itbackyard.Const;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Wet-extractor
 * Developer Maytham on 05-09-2016
 * Updated Maytham 08-08-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class LogData {

    private final Path logFile = Paths.get(Const.res + "/log/log.txt");
    private final String textFormat = "%-22s%-62s%s";
    private final String DATE_FORMAT = "dd-MM-Y HH:mm:ss";
    private final int removePackageName = "com.itbackyard.".length();

    private LogData() {
    }

    private static class LogDataHelper {
        private static final LogData INSTANCE = new LogData();
    }

    /**
     * LogData Singleton
     * @return
     */
    public static LogData getInstance() {
        return LogDataHelper.INSTANCE;
    }

    /**
     * Write action to log file
     *
     * @param action
     */
    public void write(String action) {
        write(action, null);
    }

    /**
     * Write action and message to log file
     *
     * @param action
     * @param msg
     */
    public void write(String action, String msg) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String time = format.format(Calendar.getInstance().getTime());
        String result = logFormat(action, msg, time);
        List<String> lines = Collections.singletonList(result);
        try {
            Files.write(logFile, lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get current class and method name in one line
     *
     * @return
     */
    public String getCurrentMethodName() {
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        String className = ste[2].getClassName();
        String methodName = ste[2].getMethodName();
        return className.substring(removePackageName) + " -> " + methodName;
    }

    private String logFormat(String action, String msg, String time) {
        if (msg.equals("")) msg = ("N/A");
        return String.format(textFormat, time + " ", action + " ", msg + " ");
    }

    private String logHeader() {
        StringBuilder s = new StringBuilder();
        String header = String.format(textFormat,
                "Log time",
                "Caller class/method",
                "Message\n");
        String lines = String.format(textFormat,
                "--------------------",
                "------------------------------------------------------------",
                "------------------------------------------------------------");
        s.append(header);
        s.append(lines);
        return s.toString();
    }

}
