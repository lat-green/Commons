package com.greentree.common.math.vector;

import com.greentree.common.math.Mathf;

public abstract class AbstractVector1f extends AbstractFloatVector {

	private static final long serialVersionUID = 1L;


	public static final AbstractVector1f X = new FinalVector1f(1);
	
	public AbstractVector1f() {
	}

	public AbstractVector1f(AbstractVector1f a) {
		set(a.x());
	}

	public AbstractVector1f(float x) {
		set(x);
	}

	public AbstractVector1f add(AbstractVector1f vec) {
		return add(vec.x(), this);
	}

	public <T extends AbstractVector1f> T add(AbstractVector1f vec, T dest) {
		return add(vec.x(), dest);
	}

	public AbstractVector1f add(float x) {
		return add(x, this);
	}
	public <T extends AbstractVector1f> T add(float x, T dest) {
		dest.setX(x() + x);
		return dest;
	}
	public final float distance(AbstractVector1f vec) {
		return distance(vec.x());
	}

	public final float distance(float x) {
		float dx = x - x();
		return Mathf.abs(dx);
	}

	public float distanceSquared(AbstractVector1f vec) {
		return distanceSquared(vec.x());
	}

	public float distanceSquared(float x) {
		float dx = x - x();
		return dx*dx;
	}

	public float distanse(AbstractVector1f v) {
		float dx = v.x() - x();
		return Mathf.abs(dx);
	}
	public float distanseSqr(AbstractVector1f v) {
		float dx = v.x() - x();
		return dx*dx;
	}
	public AbstractVector1f div(AbstractVector1f vec) {
		return div(vec.x());
	}

	public AbstractVector1f div(float x) {
		return div(x, this);
	}

	public AbstractVector1f div(float x, AbstractVector1f dest) {
		dest.setX(x() / x);
		return dest;
	}

	public float dot(AbstractVector1f vec) {
		return x()*vec.x();
	}
	@Override
	public final boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		AbstractVector1f other = (AbstractVector1f) obj;
		return Mathf.equals(x(), other.x());
	}

	@Override
	public float get(int index) {
		return switch(index) {
			case 0 -> x();
			default ->
			throw new IllegalArgumentException("Unexpected value: " + index);
		};
	}

	@Override
	public final int hashCode() {
		return 31*(int)(x()/Mathf.EPS);
	}

	@Override
	public float length() {
		return x();
	}

	@Override
	public float lengthSquared() {
		return x()*x();
	}

	public AbstractVector1f mul(AbstractVector1f vec) {
		return mul(vec.x());
	}

	public AbstractVector1f mul(float x) {
		return mul(x, this);
	}

	public  <T extends AbstractVector1f> T mul(float x, T dest) {
		dest.setX(x() * x);
		return dest;
	}

	public AbstractVector1f normalize() {
		return normalize(1, this);
	}

	public AbstractVector1f normalize(float radius) {
		return mul(radius / length(), this);
	}

	public <T extends AbstractVector1f> T normalize(float radius, T dest) {
		var len = length();
		if(len > 1E-9)
			return mul(radius / len, dest);
		return dest;
	}
	public <T extends AbstractVector1f> T normalize(T dest) {
		return normalize(1, dest);
	}
	public AbstractVector1f projection(AbstractVector1f norm) {
		return projection(norm, this);
	}
	public <T extends AbstractVector1f>  T projection(AbstractVector1f norm, T dest) {
		return norm.normalize(dot(norm), dest);
	}
	public AbstractVector1f set(AbstractVector1f vec) {
		return set(vec.x());
	}
	public AbstractVector1f set(float x) {
		setX(x);
		return this;
	}

	@Override
	public void set(int index, float x) {
		switch(index) {
			case 0 -> setX(x);
			default ->
			throw new IllegalArgumentException("Unexpected value: " + index);
		}
	}

	public abstract void setX(float x);

	@Override
	public int size() {
		return 1;
	}

	public AbstractVector1f sub(AbstractVector1f vec) {
		return sub(vec.x());
	}
	public <T extends AbstractVector1f> T sub(AbstractVector1f vec, T dest) {
		return sub(vec.x(), dest);
	}
	public AbstractVector1f sub(float x) {
		return sub(x, this);
	}

	public <T extends AbstractVector1f> T sub(float x, T dest) {
		dest.setX(x() - x);
		return dest;
	}

	@Override
	public final String toString() {
		return "(" + x() + ")";
	}

	public abstract float x();


}
