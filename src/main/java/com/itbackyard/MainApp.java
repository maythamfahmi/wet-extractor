package com.itbackyard;

import com.itbackyard.System.ISystem;

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

        ExecutorService service = Executors.newFixedThreadPool(3);
        service.submit(listfetcher::onStart);
        //service.submit(downloader::doStart);
        //service.submit(program::onStart);

        service.shutdown();
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

}
