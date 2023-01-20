package com.greentree.common.math.vector.ord;

import com.greentree.common.math.vector.AbstractFloatVector;

public class Link<V extends AbstractFloatVector> implements FloatVectorOrd {
	
	private final V vec;
	private final int index;
		
	public Link(V vec, int index) {
		this.vec = vec;
		this.index = index;
	}

	public V getOrigine() {
		return vec;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public float get() {
		return vec.get(index);
	}

}
