package com.greentree.action.observable;

import java.io.Serializable;
import java.util.function.Consumer;

import com.greentree.action.ListenerCloser;

public interface TypedObjectObservable<T, E> extends Serializable {
	
	ListenerCloser addListener(T t, Consumer<? super E> listener);
	ListenerCloser addListener(Consumer<? super E> listener);
	
}
