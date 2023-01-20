package com.greentree.action.observer;

import java.io.Serializable;

public interface ObjectObserver<T> extends Serializable {

	void event(T t);
	
}
