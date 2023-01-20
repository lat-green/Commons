package com.greentree.commons.util.array.proxy;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ObjectArrayProxy extends ArrayProxy<Object> {
	private static final long serialVersionUID = 1L;

	public ObjectArrayProxy(Object array) {
		super(array);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof ObjectArrayProxy)) return false;
		var arr = (ObjectArrayProxy) obj;

		return Arrays.equals(getArray(), arr.getArray());

	}

	@Override
	public Object get(int i) {
		return Array.get(array, i);
	}

	public Object[] getArray() {
		return (Object[]) array;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(getArray());
	}

	@Override
	public void set(int i, Object t) {
		Array.set(array, i, t);
	}





}
