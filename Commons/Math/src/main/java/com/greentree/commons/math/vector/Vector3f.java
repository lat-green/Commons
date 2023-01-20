package com.greentree.commons.math.vector;

import org.joml.Matrix3f;

public class Vector3f extends AbstractVector3f {

	public float x, y, z;
	
	private static final long serialVersionUID = 1L;

	public Vector3f() {
	}

	public Vector3f(AbstractVector2f f) {
		super(f);
	}
	public Vector3f(AbstractVector2f f, float z) {
		super(f, z);
	}
	public Vector3f(AbstractVector3f vec) {
		super(vec);
	}
	public Vector3f(float f) {
		super(f);
	}

	public Vector3f(float x, float y, float z) {
		super(x, y, z);
	}
	@Override
	public Vector3f add(float x, float y, float z) {
		return add(x, y, z, this);
	}
	@Override
	public Vector3f cross(AbstractVector3f vec) {
		return cross(vec, this);
	}
	public Vector3f mul(AbstractVector3f v) {
		return mul(v, this);
	}

	@Override
	public Vector3f mul(float f) {
		return mul(f, this);
	}

	@Override
	public Vector3f mul(Matrix3f mat) {
		return mul(mat, this);
	}

	@Override
	public Vector3f normalize() {
		return normalize(1, this);
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
	public void z(float z) {
		this.z = z;
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
