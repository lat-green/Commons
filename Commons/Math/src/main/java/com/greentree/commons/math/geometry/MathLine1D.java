package com.greentree.commons.math.geometry;

import java.util.Objects;
import java.util.function.Function;

import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.float2;

public class MathLine1D {


	public float min, max;

	public MathLine1D() {
		min = Float.MAX_VALUE;
		max = Float.MIN_VALUE;
	}

	public <T, C> MathLine1D(float...fs) {
		min = Float.MAX_VALUE;
		max = Float.MIN_VALUE;
		for(float t : fs) {
			if(max < t) max = t;
			if(min > t) min = t;
		}
	}

	public MathLine1D(float min, float max) {
		this.min = min;
		this.max = max;
	}

	public <T> MathLine1D(T[] objects, Function<T, Float> function) {
		min = Float.MAX_VALUE;
		max = Float.MIN_VALUE;
		for(T t : objects) {
			float v = function.apply(t);
			if(max < v) max = v;
			if(min > v) min = v;
		}
	}

	public MathLine1D cross(MathLine1D other) {
		float min = Mathf.max(this.min, other.min);
		float max = Mathf.min(this.max, other.max);
		if(min > max)return null;
		return new MathLine1D(min, max);
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		float2 other = (float2) obj;
		if(Float.floatToIntBits(min) != Float.floatToIntBits(other.x) || Float.floatToIntBits(max) != Float.floatToIntBits(other.y)) return false;
		return true;
	}
	public float getOverlay(MathLine1D other) {
		return (getSize() + other.getSize() - Mathf.abs(max + min - other.max - other.min)) / 2f;
	}

	public float getSize() {
		return max - min;
	}

	@Override
	public int hashCode() {
		return Objects.hash(min, max);
	}

	public boolean isIntersect(float x) {
		return max >= x && min <= x;
	}

	public boolean isIntersect(MathLine1D other) {
		return max >= other.min && min <= other.max;
	}

	@Override
	public String toString() {
		return "float2 [min=" + min + ", max=" + max + "]";
	}

	public MathLine1D union(MathLine1D other) {
		return new MathLine1D(Mathf.min(min, other.min), Mathf.max(max, other.max));
	}

}
