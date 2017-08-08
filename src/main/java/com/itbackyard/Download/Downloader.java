package com.itbackyard.Download;

import com.itbackyard.Const;
import com.itbackyard.Helper.FileHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * Created by Maytham on 15-05-2016.
 */
public class Downloader {

    private static String baseUrl = "https://commoncrawl.s3.amazonaws.com/";
    private static String downloadFolder = Const.res + "/files/wet/";
    private static int pools = 2;

    private static String urlList = Const.res + "/wet/wet_download_list.txt";

    private static FileHelper fileHelper = new FileHelper();

    public static void main(String[] args) throws IOException {
        System.out.println("Staring downloading...");
        //Downloader.doStart(urlList, 1);
        Stream<String> listOfUrls = fileHelper.urlList(urlList, 2);
        listOfUrls.limit(2).forEach(System.out::println);
    }

    public static void doStart(String downloadList, int maxUrls) {

        ExecutorService pool = Executors.newFixedThreadPool(pools);

        Stream<String> listOfUrls = FileHelper.urlList(downloadList, maxUrls);

        listOfUrls.limit(2).forEach(System.out::println);


        //listOfUrls.forEach(fullUrl -> {
        //System.out.println(fullUrl);
            /*String url = baseUrl + fullUrl;
            String filename = getFilenameFromUrl(url);
            if (!isFileDownloaded(downloadFolder, filename)) {
                pool.submit(new DownloadTask(url));
                try {
                    pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
        //});

        //pool.shutdown();

        /*try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
            //pool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    private static class DownloadTask implements Runnable {

        private String url;

        public DownloadTask(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            download(url);
        }
    }

    public static void download(String urlRaw) {
        try {
            String filename = getFilenameFromUrl(urlRaw);
            URL url = new URL(urlRaw);
            System.out.println("Downloading: " + filename);
            InputStream in = url.openStream();
            Files.copy(in, Paths.get(downloadFolder + filename), StandardCopyOption.REPLACE_EXISTING);
            in.close();
            System.out.print(" Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFilenameFromUrl(String url) {
        int pos = url.lastIndexOf("wet/") + 4;
        String filename = url.substring(pos, url.length());
        return filename;
    }

    public static boolean isFileDownloaded(String downloadPath, String fileName) {
        File file = new File(downloadPath + fileName);
        if (file.exists() && !file.isDirectory()) {
            System.out.println("NOTE! " + fileName + " is already downloaded");
            return true;
        }
        return false;
    }

}


