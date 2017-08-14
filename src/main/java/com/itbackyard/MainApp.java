package com.itbackyard;

import com.itbackyard.System.ISystem;
import sun.applet.Main;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Wet-extractor
 * Developer Maytham on 25-07-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class MainApp implements ISystem {

    // How many wet files do you want to donwload?
    // Each file is about 100-150Mb as GunZip.
    public int numberOfDownload = 2;

    public static void main(String[] args) throws InterruptedException {

        Thread[] t = new Thread[3];
        t[0] = new Thread(listfetcher::onStart);
        t[1] = new Thread(downloader::onStart);
        t[2] = new Thread(program::onStart);

        thread.runThreads(t);

    }



}
