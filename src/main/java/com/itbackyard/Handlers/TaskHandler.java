package com.itbackyard.Handlers;

import com.itbackyard.System.Const;
import com.itbackyard.System.AppSystem;
import com.itbackyard.Tasks.Task;

/* "Abstract Builder" */
public abstract class TaskHandler extends AppSystem {
    protected Task task;

    public Task getTask() {
        return task;
    }

    public void createTask() {
        task = new Task();
    }

    public void preExecute() {
        System.out.println(
                Const.ANSI_GREEN +
                        "Starting task... " +
                        task.getMessage() +
                        Const.ANSI_RESET
        );
    }

    public void doExecute() {
        System.out.println(
                Const.ANSI_WHITE +
                        "Executing task... " +
                        task.getMessage() +
                        Const.ANSI_RESET
        );
    }

    public void endExecute() {
        System.out.println(
                Const.ANSI_RED +
                        "Ending task... " +
                        task.getMessage() +
                        Const.ANSI_RESET
        );
    }
}