package com.greentree.commons.action.container;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.util.iterator.IteratorUtil;

import java.io.Serializable;

public interface TypedListenerContainer<T, L> extends Serializable {

    ListenerCloser add(L listener);

    ListenerCloser add(T type, L listener);

    default int size(T type) {
        return IteratorUtil.size(listeners(type));
    }

    Iterable<? extends L> listeners(T type);

    void clear();

}
