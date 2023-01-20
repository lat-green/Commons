package com.greentree.commons.action.observer.object;

import java.util.function.Consumer;

import com.greentree.commons.action.MultiAction;


/**
 * @author Arseny Latyshev
 */
public class EventAction<T> extends MultiAction<Consumer<? super T>> implements IObjectAction<T> {
	private static final long serialVersionUID = 1L;

	@Override
	public void event(T t) {
		for(var l : listeners)
			l.accept(t);
	}



}
