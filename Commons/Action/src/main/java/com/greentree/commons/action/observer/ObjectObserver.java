package com.greentree.commons.action.observer;

import java.io.Serializable;

public interface ObjectObserver<T> extends Serializable {

	void event(T t);
	
}
