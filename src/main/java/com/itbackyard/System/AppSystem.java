package com.itbackyard.System;

/**
 * Class {@code AppSystem} application system methods
 *
 * @author Maytham Fahmi
 * @see IAppSystem
 * @since WET-EXTRACTOR 3.0
 */
public class AppSystem implements IAppSystem {

    /**
     * Get current class and method name in one line
     *
     * @return String
     */
    public String getClassMethodName() {
        return getClassName() + " : " + getMethodName();
    }

    /**
     * Get current class name in one line
     *
     * @param scope depending where you call this method from so you can get the
     *              correct class name, pass 1
     * @return Caller class name
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
     * @return Caller method name
     */
    public String getMethodName() {
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }

    /**
     * Accept Array of Thread and execute sequentially.
     *
     * @param t array of threads
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
