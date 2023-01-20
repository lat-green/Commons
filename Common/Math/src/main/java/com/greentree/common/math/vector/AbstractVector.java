package com.greentree.common.math.vector;

import java.io.Serializable;

import com.greentree.common.math.Mathf;

public abstract class AbstractVector implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public abstract boolean equals(Object obj);
	
	public float length() {
		return Mathf.sqrt(lengthSquared());
	}

	public abstract float lengthSquared();
	public abstract int size();


	public final void checkSize(AbstractVector v) {
		checkSize(this, v);
	}
	
	public static void checkSize(AbstractVector a, AbstractVector b) {
		if(a.size() != b.size())
			throw new IllegalArgumentException("the size of the vectors are different " + a + " " + b);
	}
	
}
