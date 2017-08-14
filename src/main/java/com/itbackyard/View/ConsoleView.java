package com.itbackyard.View;

public class ConsoleView {

    private ConsoleView() {
    }

    private static class ConsoleViewHelper {
        private static final ConsoleView INSTANCE = new ConsoleView();
    }

    /**
     * FileHelper Singleton
     *
     * @return
     */
    public static ConsoleView getInstance() {
        return ConsoleView.ConsoleViewHelper.INSTANCE;
    }

    /**
     *
     * @param msg
     */
    public void print(String msg) {
        System.out.print(msg);
    }

    /**
     *
     * @param msg
     */
    public void println(String msg) {
        System.out.print(msg + "\n");
    }

    /**
     *
     */
    public void printDone() {
        System.out.print(" -> Done!\n");
    }

    /**
     *
     */
    public void printRefresh() {
        System.out.print("\rDone!\n");
    }

    /**
     *
     */
    public void printFinish() {
        System.out.println("Finished");
    }

}
