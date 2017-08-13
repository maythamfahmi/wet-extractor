package com.itbackyard.Download;

import com.itbackyard.Const;
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

    private final String BASE_URL = "https://commoncrawl.s3.amazonaws.com/";
    private final String DOWNLOAD_PATH = Const.res + "/wet/files/";
    private final String DOWNLOADED = Const.res + "/wet/downloaded.txt";
    public final String URL_DOWNLOAD_LIST = Const.res + "/wet/url_download_list.txt";
    private final int CORES = Runtime.getRuntime().availableProcessors();
    private final int POOLS = CORES;

    /**
     * Download wet common crawl files from Amazon S3
     *
     * @param downloadList
     * @param maxUrls
     */
    public void doStart(String downloadList, int maxUrls) {

        ExecutorService pool = Executors.newFixedThreadPool(POOLS);

        Stream<String> streamOfUrl = file.urlList(downloadList, maxUrls);

        streamOfUrl.forEach(fullUrl -> {
            String url = BASE_URL + fullUrl;
            String fileName = getFilenameFromUrl(url);
            if (!file.exist(DOWNLOAD_PATH)) {
                file.createFolder(DOWNLOAD_PATH);
            }
            // in case to check file existence
            /*|| !isFileExist(DOWNLOAD_PATH + fileName)*/
            if (!isFileDownloaded(fileName, DOWNLOADED)) {
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
            System.out.println("Downloading: " + urlRaw);
            InputStream inputStream = url.openStream();
            Files.copy(
                    inputStream,
                    Paths.get(DOWNLOAD_PATH + filename),
                    StandardCopyOption.REPLACE_EXISTING
            );
            inputStream.close();
            System.out.println("Saving: " + filename);
            addToDownloaded(filename);
        } catch (IOException e) {
            log.write(log.getCurrentMethodName(), e.getMessage());
            e.printStackTrace();
        }
    }

    protected String getFilenameFromUrl(String url) {
        int pos = url.lastIndexOf("wet/") + 4;
        return url.substring(pos, url.length());
    }

    protected boolean isFileExist(String fileName) {
        File file = new File(fileName);
        if (file.exists() && !file.isDirectory()) {
            return true;
        }
        log.write(log.getCurrentMethodName(), "?");
        return false;
    }

    protected boolean isFileDownloaded(String fileName, String fileList) {
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
            Files.write(Paths.get(DOWNLOADED),
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


