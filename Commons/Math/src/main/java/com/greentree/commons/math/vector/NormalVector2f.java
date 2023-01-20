package com.greentree.commons.math.vector;

import com.greentree.commons.math.Mathf;

public class NormalVector2f extends AbstractVector2f {
	private static final long serialVersionUID = 1L;
	
	private float angle;
	
	public NormalVector2f() {
		super();
	}

	public NormalVector2f(AbstractVector2f a) {
		super(a);
	}

	@Override
	public float lengthSquared() {
		return 1;
	}
	@Override
	public float length() {
		return 1;
	}
	
	public NormalVector2f(float x, float y) {
		super(x, y);
	}

	public NormalVector2f(float f) {
		super(f);
	}
	
	public NormalVector2f set(float x, float y) {
		angle = Mathf.acos(x);
		
		if(y < 0) angle *= -1;
		
		return this;
	}
	
	@Override
	public void x(float x) {
		throw new UnsupportedOperationException("use set(x, y)");
	}

	@Override
	public void y(float y) {
		throw new UnsupportedOperationException("use set(x, y)");
	}

	@Override
	public float x() {
		return Mathf.cos(angle);
	}

	@Override
	public float y() {
		return Mathf.cos(angle);
	}

}
