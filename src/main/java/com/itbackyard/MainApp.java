package com.itbackyard;

import com.itbackyard.Handlers.*;
import com.itbackyard.Tasks.Task;
import com.itbackyard.Tasks.TaskManager;

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