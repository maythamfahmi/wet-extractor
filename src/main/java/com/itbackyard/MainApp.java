package com.itbackyard;

import com.itbackyard.Handlers.*;

/**
 *
 */
public class MainApp {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        TaskHandler initializer = new InitializerTaskHandler();
        TaskHandler downloader = new DownloaderTaskHandler();
        TaskHandler extractor = new ExtractorTaskHandler();
        TaskHandler generator = new GeneratorTaskHandler();

        taskManager.setTaskHandler(initializer);
        taskManager.onStart();

        taskManager.setTaskHandler(downloader);
        taskManager.onStart();

        /*taskManager.setTaskHandler(extractor);
        taskManager.onStart();
        taskManager.setTaskHandler(generator);
        taskManager.onStart();*/

        Task task = taskManager.getTask();
    }
}