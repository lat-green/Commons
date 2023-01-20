package com.greentree.common.util.reference;

public interface VarReference<T> {
	
	T get();
	void set(T t);
	
}
