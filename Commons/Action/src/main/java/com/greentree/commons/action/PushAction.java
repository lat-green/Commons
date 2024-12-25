package com.greentree.commons.action;

import com.greentree.commons.action.observer.ObjectObserver;

import java.util.ArrayList;
import java.util.Collection;

public final class PushAction<T, A extends ObjectObserver<T>> implements ObjectObserver<T> {

    private static final long serialVersionUID = 1L;

    private final A action;

    private final Collection<T> stack = new ArrayList<>();

    public PushAction(A action) {
        this.action = action;
    }

    @Override
    public void clear() {
        stack.clear();
        action.clear();
    }

    @Override
    public void event(T t) {
        stack.add(t);
    }

    public void flush() {
        for (var c : stack)
            action.event(c);
        stack.clear();
    }

    public A getAction() {
        return action;
    }

}
