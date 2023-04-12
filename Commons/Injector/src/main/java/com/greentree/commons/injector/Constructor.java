package com.greentree.commons.injector;


public interface Constructor<T> {
	
	T value();
	
	void inject();
	
}