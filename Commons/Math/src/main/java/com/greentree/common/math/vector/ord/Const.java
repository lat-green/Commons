package com.greentree.common.math.vector.ord;


public class Const implements FloatVectorOrd {

	private final float value;
	
	public Const(float value) {
		this.value = value;
	}

	@Override
	public float get() {
		return value;
	}

}
