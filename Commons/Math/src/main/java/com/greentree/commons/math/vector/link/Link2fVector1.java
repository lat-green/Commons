package com.greentree.commons.math.vector.link;

import com.greentree.commons.math.vector.AbstractVector1f;
import com.greentree.commons.math.vector.AbstractVector2f;

public class Link2fVector1 extends AbstractVector1f {
	private static final long serialVersionUID = 1L;

	private final Link2f X;
	
	private final AbstractVector2f original;

	public Link2fVector1(Link2f x, AbstractVector2f original) {
		X = x;
		this.original = original;
	}

	@Override
	public void setX(float x) {
		X.set(original, x);
	}

	@Override
	public float x() {
		return X.get(original);
	}
	
}
