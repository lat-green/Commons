package com.greentree.common.math.vector;


public class FinalVector1f extends AbstractVector1f {
	
	public final float x;

	private static final long serialVersionUID = 1L;

	public FinalVector1f() {
		this(0);
	}

	public FinalVector1f(AbstractVector1f vec) {
		x = vec.x();
	}

	public FinalVector1f(float f) {
		x = f;
	}

	public FinalVector1f(float x, float y) {
		this.x = x;
	}

	@Override
	public void setX(float x) {
		throw new UnsupportedOperationException("change x of FinalVector2f " + this);
	}

	@Override
	public float x() {
		return x;
	}

}
