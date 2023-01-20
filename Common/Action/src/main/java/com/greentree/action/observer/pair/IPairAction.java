package com.greentree.action.observer.pair;

import com.greentree.action.observable.PairObservable;
import com.greentree.action.observer.PairObserver;

public interface IPairAction<T1, T2> extends PairObservable<T1, T2>, PairObserver<T1, T2> {
	
}
