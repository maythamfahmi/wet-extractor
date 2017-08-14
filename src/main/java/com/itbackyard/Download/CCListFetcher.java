package com.itbackyard.Download;

import com.itbackyard.Const;
import com.itbackyard.System.ISystem;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class CCListFetcher implements ISystem {

    //Refrence pages:
    //https://commoncrawl.s3.amazonaws.com/crawl-data/CC-MAIN-2017-30/wet.paths.gz
    //http://commoncrawl.org/the-data/get-started/CC-MAIN-2017-30/wet.paths.gz

    private CCListFetcher() {
    }

    private static class CCListFetcherHelper {
        private static final CCListFetcher INSTANCE = new CCListFetcher();
    }

    /**
     * CCListFetcher Singleton
     *
     * @return
     */
    public static CCListFetcher getInstance() {
        return CCListFetcher.CCListFetcherHelper.INSTANCE;
    }

    /**
     * @throws InterruptedException
     */
    public void onStart() {

        if (!file.exist(Const.FILE_URL_DOWNLOAD_LIST)) {

            c.println("Prepare download list...");

            Thread t1 = new Thread(() -> {
                try {
                    c.print("Downloading latest common crawl list...");
                    ccf.download(ccf.lastWetUrl());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Thread t2 = new Thread(() -> {
                c.printDone();
                c.print("Unzipping common crawl list: " + Const.FILE_GZ_DOWNLOAD_LIST);
                gzip.gzUnzip(
                        Const.FILE_GZ_DOWNLOAD_LIST,
                        Const.FILE_URL_DOWNLOAD_LIST);
            });
            Thread t3 = new Thread(() -> {
                c.printDone();
                c.print("Saving text file: " + Const.FILE_GZ_DOWNLOAD_LIST);
                file.deleteIfExists(Const.PATH_WET + Const.FILENAME_GZ);
            });
            t1.start();
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t2.start();
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t3.start();
            try {
                t3.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Return url of latest gunzip wet file
     *
     * @return
     * @throws IOException
     */
    private String lastWetUrl() throws IOException {
        try {
            Document doc = Jsoup.connect(Const.CC_URL).get();
            Elements content = doc.getElementsByClass("entry-content");
            Elements archive = content.select("li:contains(MAIN)");
            String last = archive.select("li").last().toString();
            String fileName = last.substring(last.indexOf("CC-MAIN"), last.indexOf(" ")).trim();
            return Const.BASE_URL + "crawl-data/" + fileName + "/" + Const.FILENAME_GZ;
        } catch (IOException e) {
            log.write(log.getCurrentMethodName(), e.getMessage());
            e.getStackTrace();
        }
        return Const.EMPTY_STRING;
    }

    /**
     * @param url
     * @throws IOException
     */
    private void download(String url) throws IOException {
        try {
            URL website = new URL(url);
            FileUtils.copyURLToFile(website, new File(Const.PATH_WET + Const.FILENAME_GZ));
        } catch (IOException e) {
            log.write(log.getCurrentMethodName(), e.getMessage());
            e.getStackTrace();
        }
    }

}
