package com.itbackyard;

import com.itbackyard.Download.Downloader;
import com.itbackyard.Logic.Program;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Wet-extractor
 * Developer Maytham on 25-07-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class MainApp {

    public static void main(String[] args) throws IOException, URISyntaxException {

        boolean download = false;

        if (download) {
            System.out.println("Staring downloading...");
            Downloader d = new Downloader();
            d.doStart(d.URL_DOWNLOAD_LIST, 2);
        } else {
            System.out.println("Starting...");
            new Program().onStart();
        }

    }

}
