package com.greentree.commons.action.observer.pair;

import com.greentree.commons.action.observable.PairObservable;
import com.greentree.commons.action.observer.PairObserver;

public interface IPairAction<T1, T2> extends PairObservable<T1, T2>, PairObserver<T1, T2> {

}
