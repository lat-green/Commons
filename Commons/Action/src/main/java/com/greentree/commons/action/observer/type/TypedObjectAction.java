package com.greentree.commons.action.observer.type;

import java.util.function.Consumer;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.TypedMultiAction;

public class TypedObjectAction<T, E> extends TypedMultiAction<T, Consumer<? super E>>
		implements ITypedObjectAction<T, E> {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void event(T type, E t2) {
		for(var l : listeners.listeners(type))
			l.accept(t2);
	}
	
}
