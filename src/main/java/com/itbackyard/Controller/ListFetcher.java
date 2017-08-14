package com.itbackyard.Controller;

import com.itbackyard.Const;
import com.itbackyard.System.ISystem;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ListFetcher implements ISystem {

    //Refrence pages:
    //https://commoncrawl.s3.amazonaws.com/crawl-data/CC-MAIN-2017-30/wet.paths.gz
    //http://commoncrawl.org/the-data/get-started/CC-MAIN-2017-30/wet.paths.gz

    private ListFetcher() {
    }

    private static class CCListFetcherHelper {
        private static final ListFetcher INSTANCE = new ListFetcher();
    }

    /**
     * CCListFetcher Singleton
     *
     * @return
     */
    public static ListFetcher getInstance() {
        return ListFetcher.CCListFetcherHelper.INSTANCE;
    }

    /**
     * @throws InterruptedException
     */
    public void start() {

        if (!file.exist(Const.FILE_URL_DOWNLOAD_LIST)) {

            c.println("Prepare download list...");

            Thread[] t = new Thread[3];

            t[0] = new Thread(() -> {
                String fileName = Const.PATH_WET + Const.FILENAME_GZ;
                try {
                    c.print("Downloading latest common crawl list...");
                    listfetcher.download(listfetcher.lastWetUrl(), fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            t[1] = new Thread(() -> {
                c.printDone();
                c.print("Unzipping common crawl list: " + Const.FILE_GZ_DOWNLOAD_LIST);
                gzip.gzUnzip(
                        Const.FILE_GZ_DOWNLOAD_LIST,
                        Const.FILE_URL_DOWNLOAD_LIST);
            });
            t[2] = new Thread(() -> {
                c.printDone();
                c.print("Saving text file: " + Const.FILE_GZ_DOWNLOAD_LIST);
                file.deleteIfExists(Const.PATH_WET + Const.FILENAME_GZ);
                c.printDone();
            });
            system.runThreads(t);
        }

        if (!file.exist(Const.SWEAR_WORDS)) {
            c.println("Prepare download swear list...");

            Thread[] t = new Thread[1];

            t[0] = new Thread(() -> {
                c.print("Downloading and saving Swearword list...");
                try {
                    download(Const.URL_SWEAR_WORDS, Const.SWEAR_WORDS);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                c.printDone();
            });
            system.runThreads(t);
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
            Document doc = Jsoup.connect(Const.URL_CC).get();
            Elements content = doc.getElementsByClass("entry-content");
            Elements archive = content.select("li:contains(MAIN)");
            String last = archive.select("li").last().toString();
            String fileName = last.substring(last.indexOf("CC-MAIN"), last.indexOf(" ")).trim();
            return Const.URL_BASE + "crawl-data/" + fileName + "/" + Const.FILENAME_GZ;
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
    private void download(String url, String fileName) throws IOException {
        try {
            URL website = new URL(url);
            FileUtils.copyURLToFile(website, new File(fileName));
        } catch (IOException e) {
            log.write(log.getCurrentMethodName(), e.getMessage());
            e.getStackTrace();
        }
    }

}
