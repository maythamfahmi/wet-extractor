package com.itbackyard;

import com.itbackyard.Download.Downloader;
import com.itbackyard.Helper.LogData;
import com.itbackyard.Logic.Program;
import com.itbackyard.System.ISystem;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Wet-extractor
 * Developer Maytham on 25-07-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class MainApp implements ISystem {

    private static final LogData LOG = LogData.getInstance();

    public static void main(String[] args) throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(ccf::onStart);

        /*service.submit(() -> {
            System.out.println("Staring downloading...");
            Downloader d = new Downloader();
            //d.doStart(d.URL_DOWNLOAD_LIST, 2);
        });

        service.submit(() -> {
            System.out.println("Staring processing...");
            try {
                new Program().onStart();
            } catch (IOException e) {
                LOG.write(LOG.getCurrentMethodName(), e.getMessage());
                e.printStackTrace();
            }
        });*/

        service.shutdown();

        service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

    }


    public static void main1(String[] args) throws IOException, URISyntaxException {

        Thread downloading = new Thread(() -> {
            System.out.println("Staring downloading...");
            Downloader d = new Downloader();
            //d.doStart(d.URL_DOWNLOAD_LIST, 2);
        });
        Thread processing = new Thread(() -> {
            System.out.println("Staring processing...");
            try {
                new Program().onStart();
            } catch (IOException e) {
                LOG.write(LOG.getCurrentMethodName(), e.getMessage());
                e.printStackTrace();
            }
        });
        Thread finalizing = new Thread() {

        };

        try {
            downloading.start();
            downloading.join();
            processing.start();
            //processing.join();
        } catch (InterruptedException e) {
            LOG.write(LOG.getCurrentMethodName(), e.getMessage());
            e.printStackTrace();
        }
    }

}
