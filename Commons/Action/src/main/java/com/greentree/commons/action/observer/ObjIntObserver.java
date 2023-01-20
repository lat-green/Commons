package com.greentree.commons.action.observer;

import java.io.Serializable;

public interface ObjIntObserver<T> extends Serializable {
	
	void event(T e, int i);
	
}
