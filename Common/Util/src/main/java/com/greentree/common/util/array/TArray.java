package com.greentree.common.util.array;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import com.greentree.common.util.LongHash;

public class TArray<T> extends LongHash implements Serializable {
	private static final long serialVersionUID = 1L;

	public transient int hash;

	final T[] array;

	@SafeVarargs
	public TArray(T...data) {
		this.array = data;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!super.equals(obj) || getClass() != obj.getClass()) return false;
		TArray other = (TArray) obj;
		return LongHash.equals(this, other) && Arrays.equals(array, other.array);
	}

	@Override
	public String toString() {
		return "TArray " + Arrays.toString(array);
	}

	@Override
	protected int hashCode0() {
		return Objects.hash(Arrays.hashCode(array));
	}
}