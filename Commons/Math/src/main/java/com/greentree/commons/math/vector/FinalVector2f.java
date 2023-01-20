package com.greentree.commons.math.vector;


public class FinalVector2f extends AbstractVector2f {
	public final float x, y;

	private static final long serialVersionUID = 1L;

	public FinalVector2f() {
		this(0);
	}

	public FinalVector2f(AbstractVector2f position) {
		x = position.x();
		y = position.y();
	}

	public FinalVector2f(float f) {
		x = f;
		y = f;
	}

	public FinalVector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void x(float x) {
		throw new UnsupportedOperationException("change x of FinalVector2f " + this);
	}

	@Override
	public void y(float y) {
		throw new UnsupportedOperationException("change y of FinalVector2f " + this);
	}

	@Override
	public float x() {
		return x;
	}

	@Override
	public float y() {
		return y;
	}

}
