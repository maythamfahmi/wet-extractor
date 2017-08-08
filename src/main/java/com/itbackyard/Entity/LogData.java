package com.itbackyard.Entity;

import com.itbackyard.Const;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Wet-extractor
 * Developer Maytham on 05-09-2016
 * 2017 © Copyright | ITBackyard ApS
 */
public class LogData {

    private Path logFile = Paths.get(Const.res + "/log/log.txt");
    private final int removePackageName = "com.itbackyard.".length();
    private final String textFormat = "%-22s%-42s%s";
    private final String DATE_FORMAT = "dd-MM-Y HH:mm:ss";

    private LogData() {
    }

    private static class LogDataHelper {
        private static final LogData INSTANCE = new LogData();
    }

    public static LogData getInstance() {
        return LogDataHelper.INSTANCE;
    }

    public void write(String action) {
        write(action, null);
    }

    public void write(String action, String msg) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String time = format.format(Calendar.getInstance().getTime());
        String result = logFormat(action, msg, time);
        List<String> lines = Arrays.asList(result);
        try {
            Files.write(logFile, lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String logFormat(String action, String msg, String time) {
        if (msg.equals("")) msg = ("N/A");
        return String.format(textFormat, time + " ", action + " ", msg + " ");
    }

    private String logHeader() {
        StringBuilder s = new StringBuilder();
        String header = String.format(textFormat, "Log time", "Caller class/method", "Message\n");
        String lines = String.format(textFormat, "--------------------", "----------------------------------------", "------------------------------------------------------------");
        s.append(header);
        s.append(lines);
        return s.toString();
    }

    public static void main(String[] args) {
        String s = LogData.getInstance().logHeader();
        String s1 = LogData.getInstance().logFormat("xx", "xxmesg", "22");
        System.out.println(s);
    }

    public String getCurrentMethodName() {
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        String className = ste[2].getClassName();
        String methodName = ste[2].getMethodName();
        return className.substring(removePackageName) + " -> " + methodName;
    }

}
