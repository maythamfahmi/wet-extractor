package com.itbackyard.Tasks;

import com.itbackyard.System.Const;
import com.itbackyard.Helpers.DownloadHelper;
import com.itbackyard.System.AppSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * WetExtractor
 * Developer Maytham on 05-09-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class InitializerTask extends AppSystem {

    private Map<String, String> createSystemFolders2() {
        String input = "src/main/resources/folder.properties";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(input));
            return reader.lines()
                    .filter(f -> !f.startsWith("# "))
                    .map(elem -> elem.split("="))
                    .collect(Collectors.toMap(e -> e[0], e -> e[1]));
        } catch (IOException e) {
            log.error(getClassMethodName(), e);
            return new HashMap<>();
        }
    }

    public void createSystemFolders1() {
        Map<String, String> x = createSystemFolders2();
        String root = x.get("root");
        x.entrySet()
                .stream()
                .filter(p -> !p.getKey().equals("root"))
                .forEach(e -> file.createFolder(root + e.getValue()));
    }

    /**
     * Create initial folder and file structure if not exist
     */
    public void createSystemStructure() {
        // Folder structure
        for (String s : Const.folderStructure) {
            if (!file.exist(s)) {
                file.createFolder(s);
            }
        }

        // File structure
        String downloaded = Const.FILE_DOWNLOADED;
        if (!file.exist(downloaded)) {
            file.createFile(downloaded, new ArrayList<>());
        }
    }

    /**
     * Download latest download list
     */
    public void downloadList() {
        String filename = Const.FILE_WET_GZ;
        String url = file.getLastWetUrl();
        if (!file.exist(Const.FILE_URL_LIST)) {
            new DownloadHelper(url, filename).run();
        }
    }

    /**
     * Unzip GZ file to .txt
     */
    public void unzipList() {
        if (!file.exist(Const.FILE_URL_LIST)) {
            gzip.gzUnzip(
                    Const.FILE_WET_GZ,
                    Const.FILE_URL_LIST
            );
        }
    }

    /**
     * Delete GZ file after unzipped
     */
    public void deleteList() {
        if (file.exist(Const.FILE_WET_GZ)) {
            file.deleteIfExists(Const.FILE_WET_GZ);
        }
    }

    /**
     * Download and save Swear word list
     */
    public void downloadSwearList() {
        if (!file.exist(Const.FILE_SWEAR_WORDS)) {
            new DownloadHelper(
                    Const.URL_SWEAR_WORDS,
                    Const.FILE_SWEAR_WORDS)
                    .run();
        }
    }

}
