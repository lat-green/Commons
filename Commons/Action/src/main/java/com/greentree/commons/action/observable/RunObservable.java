package com.greentree.commons.action.observable;

import com.greentree.commons.action.ListenerCloser;

import java.io.Serializable;

public interface RunObservable extends Serializable {

    RunObservable NULL = new RunObservable() {

        private static final long serialVersionUID = 1L;

        @Override
        public ListenerCloser addListener(Runnable listener) {
            return ListenerCloser.EMPTY;
        }

        @Override
        public int listenerSize() {
            return 0;
        }
    };

    ListenerCloser addListener(Runnable listener);

    default boolean isEmpty() {
        return listenerSize() == 0;
    }

    default int listenerSize() {
        throw new UnsupportedOperationException();
    }

}
