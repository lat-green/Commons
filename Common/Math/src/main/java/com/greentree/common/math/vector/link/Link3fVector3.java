package com.greentree.common.math.vector.link;

import com.greentree.common.math.vector.AbstractVector3f;

public class Link3fVector3 extends AbstractVector3f {
	private static final long serialVersionUID = 1L;

	private final Link3f X, Y, Z;

	private final AbstractVector3f original;

	public Link3fVector3(Link3f x, Link3f y, Link3f z, AbstractVector3f original) {
		X = x;
		Y = y;
		Z = z;
		this.original = original;
	}

	@Override
	public void x(float x) {
		X.set(original, x);
	}

	@Override
	public void y(float y) {
		Y.set(original, y);
	}

	@Override
	public void z(float z) {
		Z.set(original, z);
	}

	@Override
	public float x() {
		return X.get(original);
	}

	@Override
	public float y() {
		return Y.get(original);
	}

	@Override
	public float z() {
		return Z.get(original);
	}
}
