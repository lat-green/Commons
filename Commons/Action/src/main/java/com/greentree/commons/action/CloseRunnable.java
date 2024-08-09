package com.greentree.commons.action;

public record CloseRunnable(ListenerCloser listenerCloser) implements Runnable {

    @Override
    public void run() {
        listenerCloser.close();
    }

}
