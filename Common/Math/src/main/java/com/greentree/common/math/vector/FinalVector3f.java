package com.greentree.common.math.vector;


public class FinalVector3f extends AbstractVector3f {
	public final float x, y, z;

	private static final long serialVersionUID = 1L;

	public FinalVector3f() {
		this(0);
	}

	public FinalVector3f(AbstractVector2f position, float z) {
		x = position.x();
		y = position.y();
		this.z = z;
	}

	public FinalVector3f(AbstractVector3f position) {
		x = position.x();
		y = position.y();
		z = position.z();
	}

	public FinalVector3f(float f) {
		x = f;
		y = f;
		z = f;
	}

	public FinalVector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void x(float x) {
		throw new UnsupportedOperationException("change x of FinalVecto3f " + this);
	}

	@Override
	public void y(float y) {
		throw new UnsupportedOperationException("change y of FinalVector3f " + this);
	}

	@Override
	public void z(float z) {
		throw new UnsupportedOperationException("change y of FinalVector3f " + this);
	}

	@Override
	public float x() {
		return x;
	}

	@Override
	public float y() {
		return y;
	}
	@Override
	public float z() {
		return z;
	}

}
