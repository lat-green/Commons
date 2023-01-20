package com.greentree.action.observer.type;

import com.greentree.action.observable.TypedObjectObservable;
import com.greentree.action.observer.ObjectObserver;
import com.greentree.action.observer.PairObserver;

public interface IFuncTypedObjectAction<T, E> extends TypedObjectObservable<T, E>, ObjectObserver<E>, PairObserver<T, E> {
	
	@Override
	default void event(E t) {
		event(getType(t), t);
	}

	T getType(E t);
	
}
