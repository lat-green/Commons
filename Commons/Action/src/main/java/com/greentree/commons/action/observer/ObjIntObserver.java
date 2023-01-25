package com.greentree.commons.action.observer;

public interface ObjIntObserver<T> extends Observer {
	
	void event(T e, int i);
	
}
