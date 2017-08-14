package com.itbackyard.System;

/**
 * Wet-extractor
 * Developer Maytham on 08-08-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class System implements ISystem {

    private System() {
    }

    private static class SystemHelper {
        private static final System INSTANCE = new System();
    }

    /**
     * ThreadHelper Singleton
     *
     * @return
     */
    public static System getInstance() {
        return SystemHelper.INSTANCE;
    }

    public void start() {
        Thread[] t = new Thread[4];
        t[0] = new Thread(listfetcher::start);
        t[1] = new Thread(downloader::start);
        t[2] = new Thread(program::start);
        t[3] = new Thread(small::start);

        system.runThreads(t);
    }

    /**
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
