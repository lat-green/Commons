package com.greentree.action.observer.type;

import com.greentree.action.observable.TypedObjectObservable;
import com.greentree.action.observer.PairObserver;

public interface ITypedObjectAction<T, E> extends TypedObjectObservable<T, E>, PairObserver<T, E> {
	
	
	
}
