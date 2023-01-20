package com.greentree.common.util.array.proxy;

import java.lang.reflect.Array;
import java.util.Arrays;

public class IntArrayProxy extends ArrayProxy<Integer> {
	private static final long serialVersionUID = 1L;

	public IntArrayProxy(Object array) {
		super(array);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof IntArrayProxy)) return false;
		IntArrayProxy arr = (IntArrayProxy) obj;

		return Arrays.equals(getArray(), arr.getArray());

	}

	@Override
	public Integer get(int i) {
		return Array.getInt(array, i);
	}

	public int[] getArray() {
		return (int[]) array;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(getArray());
	}

	@Override
	public void set(int i, Integer t) {
		Array.setInt(array, i, t);
	}




}
