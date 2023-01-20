package com.greentree.common.math.vector.link;

import com.greentree.common.math.vector.AbstractVector2f;
import com.greentree.common.math.vector.AbstractVector3f;

public class Link3fVector2 extends AbstractVector2f {
	private static final long serialVersionUID = 1L;

	private final Link3f X, Y;
	
	private final AbstractVector3f original;

	public Link3fVector2(Link3f x, Link3f y, AbstractVector3f original) {
		X = x;
		Y = y;
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
	public float x() {
		return X.get(original);
	}

	@Override
	public float y() {
		return Y.get(original);
	}
	
}
