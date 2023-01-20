package com.greentree.commons.action.observer.integer;

import java.util.function.IntConsumer;

import com.greentree.commons.action.MultiAction;
import com.greentree.commons.action.observable.IntObservable;
import com.greentree.commons.action.observer.IntObserver;

public class IntAction extends MultiAction<IntConsumer> implements IntObserver, IntObservable {
	private static final long serialVersionUID = 1L;

	@Override
	public void event(int i) {
		for(var l : listeners)
			l.accept(i);
	}

}
