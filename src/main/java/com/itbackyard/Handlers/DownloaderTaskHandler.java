package com.itbackyard.Handlers;

import com.itbackyard.Conf;
import com.itbackyard.Tasks.DownloaderTask;

/**
 * Class {@code DownloaderTaskHandler} download task caller
 *
 * @author Maytham Fahmi
 * @see TaskHandler
 * @since WET-EXTRACTOR 3.0
 */
public class DownloaderTaskHandler extends TaskHandler {

    @Override
    public void preExecute() {
        task.setMessage(getClassName(1));
        super.preExecute();
    }

    @Override
    public void doExecute() {
        super.doExecute();
        Thread[] t = {
                new Thread(() -> new DownloaderTask().massDownloader(Conf.MAX_URLS)),
        };
        this.runThreads(t);
    }

    @Override
    public void endExecute() {
        super.endExecute();
    }

}