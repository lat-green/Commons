package com.greentree.common.math;

import java.util.Objects;

public class float2 {

	public float x, y;
	public float2() {
	}
	public float2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		float2 other = (float2) obj;
		if(Float.floatToIntBits(x) != Float.floatToIntBits(other.x) || Float.floatToIntBits(y) != Float.floatToIntBits(other.y)) return false;
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	@Override
	public String toString() {
		return "float2 [" + x + ", " + y + "]";
	}

}
