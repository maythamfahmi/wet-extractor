package com.itbackyard.Helper;

public class ThreadHelper {

    private ThreadHelper() {
    }

    private static class ThreadHelperHelper {
        private static final ThreadHelper INSTANCE = new ThreadHelper();
    }

    /**
     * ThreadHelper Singleton
     *
     * @return
     */
    public static ThreadHelper getInstance() {
        return ThreadHelper.ThreadHelperHelper.INSTANCE;
    }

    /**
     *
     * @param t
     */
    public void runThreads(Thread[] t) {
        for (Thread thread : t) {
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
