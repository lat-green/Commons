package com.greentree.commons.action.observer;

public interface PairObserver<T1, T2> extends Observer {
	
	void event(T1 t1, T2 t2);
	
}
