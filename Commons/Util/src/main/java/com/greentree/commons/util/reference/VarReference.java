package com.greentree.commons.util.reference;

@Deprecated
public interface VarReference<T> {
	
	T get();
	void set(T t);
	
}
