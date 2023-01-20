package com.greentree.common.math.vector;

public class Vector2f extends AbstractVector2f {

	public float x, y;

	private static final long serialVersionUID = 1L;

	public Vector2f() {
	}

	public Vector2f(AbstractVector2f position) {
		super(position);
	}
	public Vector2f(float f) {
		super(f);
	}

	public Vector2f(float x, float y) {
		super(x, y);
	}

	public float getProjection(AbstractVector2f normal) {
		return 0;
	}

	@Override
	public Vector2f mul(float f) {
		return mul(f, f, this);
	}
	@Override
	public Vector2f normalize() {
		return normalize(1, this);
	}

	@Override
	public Vector2f normalize(float radius) {
		return mul(radius / length(), this);
	}

	@Override
	public void x(float x) {
		this.x = x;
	}

	@Override
	public void y(float y) {
		this.y = y;
	}

	@Override
	public Vector2f sub(AbstractVector2f vec) {
		return sub(vec, this);
	}

	@Override
	public <T extends AbstractVector2f> T sub(AbstractVector2f vec, T dest) {
		return sub(vec.x(), vec.y(), dest);
	}

	@Override
	public Vector2f sub(float x, float y) {
		return sub(x, y, this);
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
