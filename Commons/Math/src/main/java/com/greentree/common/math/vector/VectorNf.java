package com.greentree.common.math.vector;

import java.util.Arrays;

public class VectorNf extends AbstractVectorNf {
	private static final long serialVersionUID = 1L;

	private final float[] values;

	public VectorNf(VectorNf vec) {
		this.values = vec.values.clone();
	}
	
	public VectorNf(AbstractFloatVector vec) {
		this(vec.size());
		for(int i = 0; i < vec.size(); i++)
			values[i] = vec.get(i);
	}
	
	@Override
	public String toString() {
		return "VectorNf " + Arrays.toString(values);
	}

	public VectorNf(float...values) {
		this.values = values.clone();
	}
	
	public VectorNf(int length) {
		values = new float[length];
	}
	
	@Override
	public float get(int i) {
		return values[i];
	}

	@Override
	public void set(int i, float x) {
		values[i] = x;
	}

	@Override
	public int size() {
		return values.length;
	}

}
