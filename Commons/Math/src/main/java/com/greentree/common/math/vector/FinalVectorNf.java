package com.greentree.common.math.vector;


public class FinalVectorNf extends AbstractVectorNf {
	private static final long serialVersionUID = 1L;

	private final float[] values;

	public FinalVectorNf(float[] values) {
		this.values = values.clone();
	}
	
	public FinalVectorNf(int length) {
		values = new float[length];
	}
	
	@Override
	public float get(int i) {
		return values[i];
	}

	@Override
	public void set(int i, float x) {
		throw new UnsupportedOperationException("change of " + this);
	}

	@Override
	public int size() {
		return values.length;
	}

}
