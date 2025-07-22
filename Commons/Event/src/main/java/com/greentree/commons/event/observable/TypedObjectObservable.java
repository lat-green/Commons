package com.greentree.commons.event.observable;

import com.greentree.commons.action.ListenerCloser;

import java.io.Serializable;
import java.util.function.Consumer;

public interface TypedObjectObservable<T, E> extends Serializable {

    ListenerCloser addListener(T t, Consumer<? super E> listener);

    ListenerCloser addListener(Consumer<? super E> listener);

}
