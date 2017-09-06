package com.itbackyard.Tasks;

import com.itbackyard.System.Const;
import com.itbackyard.Helpers.DownloadHelper;
import com.itbackyard.System.AppSystem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


/**
 * Class {@code Download} is responsible for Download logic
 *
 * @author Maytham
 * @see com.itbackyard.System.IAppSystem
 * @since Wet-extractor3, 16-08-2017
 * <p>
 * 2017 © Copyright | Maytham, ITBackyard ApS
 */
public class DownloaderTask extends AppSystem {

    private final static int CORES = Runtime.getRuntime().availableProcessors();
    private final static int POOLS = CORES / 2;

    public void massDownloader(int maxUrls) {
        ExecutorService pool = Executors.newFixedThreadPool(POOLS);
        Stream<String> streamOfUrl =
                file.urlList(Const.FILE_URL_LIST, maxUrls);

        streamOfUrl.forEach(uri -> {
            String url = Const.URL_BASE + uri;
            String filename = file.getFilenameFromUrl(url);
            String filepath = Const.P_WET_FILES + filename;
            long filesizeOnline = file.fileSizeOnline(url);
            long filesizeLocal = -1L;
            if (file.exist(Const.P_WET_FILES + filename)) {
                filesizeLocal = file.fileSizeLocal(filepath);
            }

            if (!file.isFileDownloaded
                    (filename, Const.FILE_DOWNLOADED) ||
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
