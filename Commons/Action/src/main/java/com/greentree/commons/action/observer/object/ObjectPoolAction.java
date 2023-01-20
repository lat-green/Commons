package com.greentree.commons.action.observer.object;

import com.greentree.commons.action.PoolAction;


public class ObjectPoolAction<T> extends PoolAction<T, EventAction<T>> {
	private static final long serialVersionUID = 1L;

	public ObjectPoolAction() {
		super(new EventAction<>());
	}

	public ObjectPoolAction(EventAction<T> action) {
		super(action);
	}

}
