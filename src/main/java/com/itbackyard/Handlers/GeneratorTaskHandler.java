package com.itbackyard.Handlers;

import com.itbackyard.Tasks.ExtractorTask;
import com.itbackyard.Tasks.GeneratorTask;

/* "ConcreteBuilder" */
public class GeneratorTaskHandler extends TaskHandler {

    @Override
    public void preExecute() {
        task.setMessage(getClassName(1));
        super.preExecute();
    }

    @Override
    public void doExecute() {
        super.doExecute();
        Thread[] t = {
                new Thread(new GeneratorTask()::createSmallFiles)
        };
        this.runThreads(t);
    }

    @Override
    public void endExecute() {
        super.endExecute();
    }
}