package com.itbackyard.Tasks;

import com.itbackyard.Conf;
import com.itbackyard.Helpers.DownloadHelper;
import com.itbackyard.System.AppSystem;

import java.util.ArrayList;

/**
 * WetExtractor
 * Developer Maytham on 05-09-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class InitializerTask extends AppSystem {

    /**
     * Create initial folder and file structure if not exist
     */
    public void createSystemStructure() {
        // Folder structure
        for (String s : Conf.folderStructure) {
            if (!file.exist(s)) {
                file.createFolder(s);
            }
        }

        // File structure
        String downloaded = Conf.FILE_DOWNLOADED;
        if (!file.exist(downloaded)) {
            file.createFile(downloaded, new ArrayList<>());
        }
    }

    /**
     * Download latest download list
     */
    public void downloadList() {
        String filename = Conf.FILE_WET_GZ;
        String url = file.getLastWetUrl();
        if (!file.exist(Conf.FILE_URL_LIST)) {
            new DownloadHelper(url, filename).run();
        }
    }

    /**
     * Unzip GZ file to .txt
     */
    public void unzipList() {
        if (!file.exist(Conf.FILE_URL_LIST)) {
            gzip.gzUnzip(Conf.FILE_WET_GZ, Conf.FILE_URL_LIST);
        }
    }

    /**
     * Delete GZ file after unzipped
     */
    public void deleteList() {
        if (file.exist(Conf.FILE_WET_GZ)) {
            file.deleteIfExists(Conf.FILE_WET_GZ);
        }
    }

    /**
     * Download and save Swear word list
     */
    public void downloadSwearList() {
        if (!file.exist(Conf.FILE_SWEAR_WORDS)) {
            new DownloadHelper(Conf.URL_SWEAR_WORDS, Conf.FILE_SWEAR_WORDS).run();
        }
    }

}
