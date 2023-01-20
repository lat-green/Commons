package com.greentree.common.util.array;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import com.greentree.common.util.LongHash;

public class ByteArray extends LongHash implements Serializable {
	private static final long serialVersionUID = 1L;

	public final byte[] array;

	public ByteArray(byte...data) {
		array = data;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!super.equals(obj) || getClass() != obj.getClass()) return false;
		ByteArray other = (ByteArray) obj;
		return LongHash.equals(this, other) && Arrays.equals(array, other.array);
	}

	@Override
	public String toString() {
		return "ByteArray " + Arrays.toString(array);
	}

	protected int hashCode0() {
		return Objects.hash(Arrays.hashCode(array));
	}
}