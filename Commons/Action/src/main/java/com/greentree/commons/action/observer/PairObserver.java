package com.greentree.commons.action.observer;

import java.io.Serializable;

public interface PairObserver<T1, T2> extends Serializable {

	void event(T1 t1, T2 t2);
	
}
