package com.itbackyard.Handlers;

import com.itbackyard.Tasks.ExtractorTask;

/**
 * Class {@code ExtractorTaskHandler} extractor task caller
 *
 * @author Maytham Fahmi
 * @see TaskHandler
 * @since WET-EXTRACTOR 3.0
 */
public class ExtractorTaskHandler extends TaskHandler {

    @Override
    public void preExecute() {
        task.setMessage(getClassName(1));
        super.preExecute();
    }

    @Override
    public void doExecute() {
        super.doExecute();
        Thread[] t = {
                new Thread(new ExtractorTask()::extract),
        };
        this.runThreads(t);
    }

    @Override
    public void endExecute() {
        super.endExecute();
    }

}