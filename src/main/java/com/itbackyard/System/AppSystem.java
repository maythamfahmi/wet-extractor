package com.itbackyard.System;

/**
 * WetExtractor
 * Developer Maytham on 05-09-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class AppSystem implements IAppSystem {

    /**
     * Get current class and method name in one line
     *
     * @return
     */
    public String getClassMethodName() {
        return getClassName() + " : " + getMethodName();
    }

    /**
     * Get current class name in one line
     *
     * @return
     */
    public String getClassName(int scope) {
        String packageName = this.getClass().getPackage().getName();
        return Thread.currentThread().getStackTrace()[3 - scope].getClassName()
                .substring(packageName.length() + 1);
    }

    public String getClassName() {
        return getClassName(0);
    }

    /**
     * Get current method name in one line
     *
     * @return
     */
    public String getMethodName() {
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }

    /**
     * Accpet Array of Thread and execute sequentially.
     *
     * @param t
     */
    public void runThreads(Thread[] t) {
        for (Thread thread : t) {
            if (thread != null) {
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
