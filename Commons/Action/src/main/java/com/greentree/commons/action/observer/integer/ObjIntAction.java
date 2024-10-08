package com.greentree.commons.action.observer.integer;

import java.util.function.ObjIntConsumer;

import com.greentree.commons.action.MultiAction;
import com.greentree.commons.action.observable.ObjIntObservable;
import com.greentree.commons.action.observer.ObjIntObserver;

public final class ObjIntAction<T> extends MultiAction<ObjIntConsumer<? super T>>
		implements ObjIntObservable<T>, ObjIntObserver<T> {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void event(T e, int i) {
		for(var l : listeners)
			l.accept(e, i);
	}
	
}
