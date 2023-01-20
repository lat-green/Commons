package com.greentree.common.util.array.proxy;

import java.util.Arrays;

public class TArrayProxy<T> extends ArrayProxy<T> {
	
	private static final long serialVersionUID = 1L;
	
	@SafeVarargs
	public TArrayProxy(T... array) {
		super(array);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof TArrayProxy))
			return false;
		@SuppressWarnings("rawtypes")
		var arr = (TArrayProxy) obj;
		
		return Arrays.equals(getArray(), arr.getArray());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T get(int i) {
		return ((T[]) array)[i];
	}
	
	@SuppressWarnings("unchecked")
	public T[] getArray() {
		return (T[]) array;
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(getArray());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void set(int i, T t) {
		((T[]) array)[i] = t;
	}
	
	
	
	
	
}
