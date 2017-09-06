package com.itbackyard.Handlers;

import com.itbackyard.Conf;
import com.itbackyard.System.AppSystem;
import com.itbackyard.Tasks.Task;

/**
 * Class {@code TaskHandler} is Abstract builder
 *
 * @author Maytham Fahmi
 * @see TaskHandler
 * @since WET-EXTRACTOR 3.0
 */
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
                Conf.ANSI_GREEN +
                        "Starting task... " +
                        task.getMessage() +
                        Conf.ANSI_RESET
        );
    }

    public void doExecute() {
        System.out.println(
                Conf.ANSI_WHITE +
                        "Executing task... " +
                        task.getMessage() +
                        Conf.ANSI_RESET
        );
    }

    public void endExecute() {
        System.out.println(
                Conf.ANSI_RED +
                        "Ending task... " +
                        task.getMessage() +
                        Conf.ANSI_RESET
        );
    }
}