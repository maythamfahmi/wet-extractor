package com.itbackyard;

import com.itbackyard.Handlers.*;
import com.itbackyard.System.AppSystem;
import com.itbackyard.Tasks.Task;
import com.itbackyard.Tasks.TaskManager;

/**
 * Class {@code MainApp} starting app
 *
 * @author Maytham Fahmi
 * @see AppSystem
 * @since WET-EXTRACTOR 3.0
 */
public class MainApp {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        taskManager.setTaskHandler(new InitializerTaskHandler());
        taskManager.onStart();
        taskManager.setTaskHandler(new DownloaderTaskHandler());
        taskManager.onStart();
        taskManager.setTaskHandler(new ExtractorTaskHandler());
        taskManager.onStart();
        taskManager.setTaskHandler(new GeneratorTaskHandler());
        taskManager.onStart();

        Task task = taskManager.getTask();
    }
}