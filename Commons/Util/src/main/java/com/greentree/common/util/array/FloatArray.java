package com.greentree.common.util.array;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import com.greentree.common.util.LongHash;

public class FloatArray extends LongHash implements Serializable {
	private static final long serialVersionUID = 1L;

	public final float[] array;

	public FloatArray(float...data) {
		array = data;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!super.equals(obj) || getClass() != obj.getClass()) return false;
		FloatArray other = (FloatArray) obj;
		return LongHash.equals(this, other) && Arrays.equals(array, other.array);
	}

	@Override
	public String toString() {
		return "FloatArray " + Arrays.toString(array);
	}

	@Override
	protected int hashCode0() {
		return Objects.hash(Arrays.hashCode(array));
	}
}