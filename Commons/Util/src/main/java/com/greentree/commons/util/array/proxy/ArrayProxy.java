package com.greentree.commons.util.array.proxy;

import java.io.Serializable;
import java.lang.reflect.Array;


public abstract class ArrayProxy<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	protected final Object array;

	public ArrayProxy(Object array) {
		this.array = array;
	}

	@SuppressWarnings("rawtypes")
	public static ArrayProxy build(Object array) {
		if(array instanceof int[]) return new IntArrayProxy(array);
		if(array instanceof Object[]) return new ObjectArrayProxy(array);

		throw new IllegalArgumentException("");
	}

	@Override
	public abstract boolean equals(Object obj);

	public abstract T get(int i);

	@Override
	public abstract int hashCode();


	public int length() {
		return Array.getLength(array);
	}

	public abstract void set(int i, T t);

}
