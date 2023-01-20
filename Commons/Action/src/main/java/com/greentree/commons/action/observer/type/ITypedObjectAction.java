package com.greentree.commons.action.observer.type;

import com.greentree.commons.action.observable.TypedObjectObservable;
import com.greentree.commons.action.observer.PairObserver;

public interface ITypedObjectAction<T, E> extends TypedObjectObservable<T, E>, PairObserver<T, E> {
	
	
	
}
