package com.greentree.commons.math.value;

import java.util.Objects;

public class SimpleValue implements Value {

	private final float value;

	public SimpleValue(float value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		SimpleValue other = (SimpleValue) obj;
		if(Float.floatToIntBits(value) != Float.floatToIntBits(other.value)) return false;
		return true;
	}

	@Override
	public float getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return "SimpleValue [value=" + value + "]";
	}

}
