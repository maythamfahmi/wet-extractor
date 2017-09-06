package com.itbackyard.Handlers;

/* "ConcreteBuilder" */
public class ExtractorTaskHandler extends TaskHandler {

    @Override
    public void preExecute() {
        super.preExecute();
    }

    @Override
    public void doExecute() {
        super.doExecute();
        Thread[] t = {
                new Thread(),
        };
        this.runThreads(t);
    }

    @Override
    public void endExecute() {
        super.endExecute();
    }
}