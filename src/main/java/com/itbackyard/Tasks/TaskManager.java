package com.itbackyard.Tasks;

import com.itbackyard.Handlers.TaskHandler;

/**
 *
 */
public class TaskManager {
    private TaskHandler taskHandler;

    public void setTaskHandler(TaskHandler taskHandler) {
        this.taskHandler = taskHandler;
    }

    public Task getTask() {
        return taskHandler.getTask();
    }

    public void onStart() {
        taskHandler.createTask();
        taskHandler.preExecute();
        taskHandler.doExecute();
        taskHandler.endExecute();
    }
}