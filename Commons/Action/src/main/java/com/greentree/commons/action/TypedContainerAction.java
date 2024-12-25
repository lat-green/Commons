package com.greentree.commons.action;

import com.greentree.commons.action.container.TypedListenerContainer;

/** @author Arseny Latyshev */
public abstract class TypedContainerAction<T, L, LC extends TypedListenerContainer<T, L>> {

    protected final LC listeners;

    public TypedContainerAction(LC container) {
        this.listeners = container;
    }

    public void clear() {
        listeners.clear();
    }

    @Override
    public String toString() {
        String builder = "MultiAction [" +
                         listeners +
                         "]";
        return builder;
    }

    public ListenerCloser addListener(T t, L listener) {
        return listeners.add(t, listener);
    }

    public ListenerCloser addListener(L listener) {
        return listeners.add(listener);
    }

}
