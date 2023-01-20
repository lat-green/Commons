package com.greentree.commons.math.value;

import com.greentree.commons.math.Mathf;

public class RengeValue implements Value {

	private final float min, max;

	public RengeValue(float min, float max) {
		this.min = min;
		this.max = max;
	}


	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		RengeValue other = (RengeValue) obj;
		if(Float.floatToIntBits(max) != Float.floatToIntBits(other.max) || Float.floatToIntBits(min) != Float.floatToIntBits(other.min)) return false;
		return true;
	}


	public float getMax() {
		return max;
	}


	public float getMin() {
		return min;
	}

	@Override
	public float getValue() {
		return Mathf.lerp(min, max);
	}


	@Override
	public int hashCode() {
		int result = 31 * 1 + Float.floatToIntBits(max*2);
		result = 31 * result + Float.floatToIntBits(min);
		return result;
	}


	@Override
	public String toString() {
		return "Renge [min=" + min + ", max=" + max + "]";
	}



}
