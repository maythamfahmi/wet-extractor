package com.itbackyard.Helper;

import com.itbackyard.Entity.LogData;

import java.util.ArrayList;
import java.util.List;

/**
 * By Maytham on 07-09-2016.
 */
class LogHelper {

    static List<LogData> logList = new ArrayList<>();
    static List<String> staticsList = new ArrayList<>();

    static String getCurrentMethodName() {
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();

        //return ste[ste.length - 1 - depth].getMethodName();
        return ste[2].getClassName() + ", " + ste[2].getMethodName();
    }

    static void doLog(String methodName) {
        //logList.add(new LogData(methodName));
        System.out.println(methodName);
    }

    static void doLog(String methodName, String msg) {
        //logList.add(new LogData(methodName, msg));
        System.out.println(methodName);
    }

}
