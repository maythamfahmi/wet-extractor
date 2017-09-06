package com.itbackyard.Handlers;

import com.itbackyard.Tasks.InitializerTask;

/**
 * Class {@code InitializerTaskHandler} initializer task caller
 *
 * @author Maytham Fahmi
 * @see TaskHandler
 * @since WET-EXTRACTOR 3.0
 */
public class InitializerTaskHandler extends TaskHandler {

    @Override
    public void preExecute() {
        task.setMessage(getClassName(1));
        super.preExecute();
    }

    @Override
    public void doExecute() {
        super.doExecute();
        Thread[] t = {
                new Thread(new InitializerTask()::createSystemStructure),
                new Thread(new InitializerTask()::downloadList),
                new Thread(new InitializerTask()::downloadSwearList),
                new Thread(new InitializerTask()::unzipList),
                new Thread(new InitializerTask()::deleteList)
        };
        this.runThreads(t);
    }

    @Override
    public void endExecute() {
        super.endExecute();
    }

}