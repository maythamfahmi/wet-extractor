package com.itbackyard.Handlers;

import com.itbackyard.Tasks.GeneratorTask;

/**
 * Class {@code ExtractorTaskHandler} generator task caller
 *
 * @author Maytham Fahmi
 * @see TaskHandler
 * @since WET-EXTRACTOR 3.0
 */
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