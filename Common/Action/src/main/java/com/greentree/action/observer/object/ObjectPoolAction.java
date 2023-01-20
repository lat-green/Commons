package com.greentree.action.observer.object;

import com.greentree.action.PoolAction;


public class ObjectPoolAction<T> extends PoolAction<T, EventAction<T>> {
	private static final long serialVersionUID = 1L;

	public ObjectPoolAction() {
		super(new EventAction<>());
	}

	public ObjectPoolAction(EventAction<T> action) {
		super(action);
	}

}
