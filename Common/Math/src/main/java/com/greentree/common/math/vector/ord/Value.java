package com.greentree.common.math.vector.ord;


public class Value implements ChangeableFloatVectorOrd {

	private float value;
	
	public Value() {
	}
	
	public Value(float value) {
		this.value = value;
	}

	@Override
	public float get() {
		return value;
	}

	@Override
	public void set(float x) {
		value = x;
	}

}
