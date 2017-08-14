package com.itbackyard.Controller;

import com.itbackyard.Const;
import com.itbackyard.MainApp;
import com.itbackyard.System.ISystem;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Wet-extractor
 * Developer Maytham on 15-05-2016
 * Updated Maytham 08-08-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class Downloader implements ISystem {

    private final int CORES = Runtime.getRuntime().availableProcessors();
    private final int POOLS = CORES;

    private Downloader() {
    }

    private static class DownloaderHelper {
        private static final Downloader INSTANCE = new Downloader();
    }

    /**
     * Downloader Singleton
     *
     * @return
     */
    public static Downloader getInstance() {
        return Downloader.DownloaderHelper.INSTANCE;
    }

    /**
     * Download wet common crawl files from Amazon S3
     */
    public void doStart() {

        ExecutorService pool = Executors.newFixedThreadPool(POOLS);
        int maxUrls = new MainApp().numberOfDownload;
        Stream<String> streamOfUrl = file.urlList(Const.FILE_URL_DOWNLOAD_LIST, maxUrls);

        c.println("Prepare download of wet files...");

        streamOfUrl.forEach(fullUrl -> {
            String url = Const.URL_BASE + fullUrl;
            String fileName = getFilenameFromUrl(url);
            if (!file.exist(Const.FILES_WET_PATH)) {
                file.createFolder(Const.FILES_WET_PATH);
            }
            // in case to check file existence
            /*|| !isFileExist(DOWNLOAD_PATH + fileName)*/
            if (!isFileDownloaded(fileName, Const.FILE_DOWNLOADED)) {
                pool.submit(new DownloadTask(url));
                try {
                    pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    log.write(log.getCurrentMethodName(), e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        pool.shutdown();

        // this to let the system wait until download is finished
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
            //c.printFinish();
        } catch (InterruptedException e) {
            log.write(log.getCurrentMethodName(), e.getMessage());
            e.printStackTrace();
        }
    }

    class DownloadTask implements Runnable {

        private String url;

        DownloadTask(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            download(url);
        }
    }

    protected void download(String urlRaw) {
        try {
            String filename = getFilenameFromUrl(urlRaw);
            URL url = new URL(urlRaw);
            c.println("Downloading: " + urlRaw);
            InputStream inputStream = url.openStream();
            Files.copy(
                    inputStream,
                    Paths.get(Const.FILES_WET_PATH + filename),
                    StandardCopyOption.REPLACE_EXISTING
            );
            inputStream.close();
            c.println("Saving: " + filename);
            addToDownloaded(filename);
        } catch (IOException e) {
            log.write(log.getCurrentMethodName(), e.getMessage());
            e.printStackTrace();
        }
    }

    public String getFilenameFromUrl(String url) {
        int pos = url.lastIndexOf("wet/") + 4;
        return url.substring(pos, url.length());
    }

    public boolean isFileExist(String fileName) {
        File file = new File(fileName);
        if (file.exists() && !file.isDirectory()) {
            return true;
        }
        log.write(log.getCurrentMethodName(), "?");
        return false;
    }

    public boolean isFileDownloaded(String fileName, String fileList) {
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(fileList)
            );
            String inLine;
            while ((inLine = reader.readLine()) != null) {
                if (inLine.equals(fileName)) {
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            log.write(log.getCurrentMethodName(), e.getMessage());
            return false;
            //e.printStackTrace();
        }
        return false;
    }

    protected void addToDownloaded(String fileName) {
        try {
            Files.write(Paths.get(Const.FILE_DOWNLOADED),
                    Collections.singletonList(fileName),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.write(log.getCurrentMethodName(), e.getMessage());
            e.printStackTrace();
        }
    }

}


