package com.itbackyard.Handlers;

import com.itbackyard.Tasks.DownloaderTask;

/* "ConcreteBuilder" */
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
                new Thread(() -> new DownloaderTask().massDownloader(1)),
        };
        this.runThreads(t);
    }

    @Override
    public void endExecute() {
        super.endExecute();
    }
}