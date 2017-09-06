package com.itbackyard.Tasks;

import com.itbackyard.Conf;
import com.itbackyard.Helpers.DownloadHelper;
import com.itbackyard.System.AppSystem;
import com.itbackyard.System.IAppSystem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Class {@code DownloaderTask} mass downloading
 *
 * @author Maytham Fahmi
 * @see IAppSystem
 * @since WET-EXTRACTOR 3.0
 */
public class DownloaderTask extends AppSystem {

    private final static int CORES = Runtime.getRuntime().availableProcessors();
    private final static int POOLS = CORES / 2;

    /**
     * The maximum number of urls to be downloaded
     *
     * @param maxUrls maximum number of urls
     */
    public void massDownloader(int maxUrls) {
        ExecutorService pool = Executors.newFixedThreadPool(POOLS);
        Stream<String> streamOfUrl =
                file.urlList(Conf.FILE_URL_LIST, maxUrls);

        streamOfUrl.forEach(uri -> {
            String url = Conf.URL_BASE + uri;
            String filename = file.getFilenameFromUrl(url);
            String filepath = Conf.P_WET_FILES + filename;
            long filesizeOnline = file.fileSizeOnline(url);
            long filesizeLocal = -1L;
            if (file.exist(Conf.P_WET_FILES + filename)) {
                filesizeLocal = file.fileSizeLocal(filepath);
            }

            if (!file.isFileDownloaded
                    (filename, Conf.FILE_DOWNLOADED) ||
                    filesizeOnline != filesizeLocal) {
                pool.submit(new DownloadHelper(url, filepath, filesizeOnline));
                try {
                    pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    log.error(getClassMethodName(), e);
                }
            }
        });

        pool.shutdown();

        // this to let the system wait until download is finished
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error(getClassMethodName(), e);
        }
    }
}
