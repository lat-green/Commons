package com.greentree.commons.util.reference;

public interface VarReference<T> {
	
	T get();
	void set(T t);
	
}
