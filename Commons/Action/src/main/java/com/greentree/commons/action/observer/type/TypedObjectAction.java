package com.greentree.commons.action.observer.type;

import com.greentree.commons.action.TypedMultiAction;

import java.util.function.Consumer;

public class TypedObjectAction<T, E> extends TypedMultiAction<T, Consumer<? super E>>
        implements ITypedObjectAction<T, E> {

    private static final long serialVersionUID = 1L;

    @Override
    public void event(T type, E t2) {
        for (var l : listeners.listeners(type))
            l.accept(t2);
    }

}
