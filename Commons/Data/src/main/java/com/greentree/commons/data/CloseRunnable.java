package com.greentree.commons.data;

import com.greentree.commons.action.ListenerCloser;

public record CloseRunnable(ListenerCloser listenerCloser) implements Runnable {

    @Override
    public void run() {
        listenerCloser.close();
    }

}
