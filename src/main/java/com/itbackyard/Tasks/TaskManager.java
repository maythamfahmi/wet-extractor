package com.itbackyard.Tasks;

import com.itbackyard.Handlers.TaskHandler;
import com.itbackyard.System.AppSystem;

/**
 * Class {@code TaskManager} task manager
 *
 * @author Maytham Fahmi
 * @see AppSystem
 * @since WET-EXTRACTOR 3.0
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