package com.itbackyard.Helpers;

import com.itbackyard.Const;
import com.itbackyard.System.AppSystem;
import com.itbackyard.System.IAppSystem;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;

/**
 * Class {@code DownloadTask} is responsible for downloading logic
 *
 * @author Maytham Fahmi
 * @see IAppSystem
 * @since WET-EXTRACTOR 3.0
 */
public class DownloadHelper extends AppSystem implements IAppSystem, Runnable {

    private String url;
    private String filepath;
    private long filesizeOnline = -2L;

    /**
     * Constructs an <code>DownloadTask</code>.
     *
     * @param url            Url (http/https) for content to download
     * @param filepath       Distention file and path name on local storage
     * @param filesizeOnline Source file size in Kb
     */
    public DownloadHelper(String url, String filepath, long filesizeOnline) {
        this.url = url;
        this.filepath = filepath;
        this.filesizeOnline = filesizeOnline;
    }

    /**
     * Constructs an <code>DownloadTask</code>.
     *
     * @param url      Url (http/https) for content to download
     * @param filepath Distention file and path name on local storage
     */
    public DownloadHelper(String url, String filepath) {
        this.url = url;
        this.filepath = filepath;
    }

    /**
     * Run Thread of instance
     */
    @Override
    public void run() {
        download();
    }

    /**
     * Download from url and save it on disk
     */
    private void download() {
        try {
            System.out.print("Downloading: " + url);
            FileUtils.copyURLToFile(new URL(url), new File(filepath));
            System.out.print(" Done!");
            //retry or done
            if (filesizeOnline > 0) {
                if (filesizeOnline != file.fileSizeLocal(filepath)) {
                    FileUtils.copyURLToFile(new URL(url), new File(filepath));
                    System.out.print("\b\b\b\b\b\b Retry!");
                }
            }
            file.createFile(
                    Const.P_SYS + Const.FILE_DOWNLOADED
                    , Collections.singletonList(file.getFilename(filepath))
            );
        } catch (IOException e) {
            log.error(getClassMethodName(), e);
        }
        System.out.println();
    }

}