package com.greentree.common.math.vector;

import org.joml.Matrix2f;

import com.greentree.common.math.Mathf;

public abstract class AbstractVector2f extends AbstractFloatVector {

	private static final long serialVersionUID = 1L;

	public static final AbstractVector2f X = new FinalVector2f(1, 0);
	public static final AbstractVector2f Y = new FinalVector2f(0, 1);
	
	public AbstractVector2f() {
	}

	public AbstractVector2f(AbstractVector2f a) {
		set(a.x(), a.y());
	}

	public AbstractVector2f(float f) {
		set(f);
	}

	public AbstractVector2f(float x, float y) {
		set(x, y);
	}

	public AbstractVector2f add(AbstractVector2f vec) {
		return add(vec.x(), vec.y(), this);
	}

	public <T extends AbstractVector2f> T add(AbstractVector2f vec, T dest) {
		return add(vec.x(), vec.y(), dest);
	}

	public AbstractVector2f add(float x, float y) {
		return add(x, y, this);
	}
	public <T extends AbstractVector2f> T add(float x, float y, T dest) {
		dest.x(x() + x);
		dest.y(y() + y);
		return dest;
	}
	public float cross(AbstractVector2f vec) {
		return x()*vec.y() - y()*vec.x();
	}
	public final float distance(AbstractVector2f vec) {
		return distance(vec.x(), vec.y());
	}
	
	@Override
	public AbstractVector2f normalize() {
		return normalize(this);
	}
	
	public final float distance(float x, float y) {
		return Mathf.sqrt(distanceSqr(x, y));
	}
	public float distanceSqr(AbstractVector2f vec) {
		return distanceSqr(vec.x(), vec.y());
	}
	public float distanceSqr(float x, float y) {
		float dx = x - x();
		float dy = y - y();
		return dx*dx + dy*dy;
	}
	public AbstractVector2f div(AbstractVector2f vec) {
		return div(vec.x(), vec.y());
	}

	public AbstractVector2f div(float x, float y) {
		return div(x, y, this);
	}
	public AbstractVector2f div(float x, float y, AbstractVector2f dest) {
		dest.x(x() / x);
		dest.y(y() / y);
		return dest;
	}

	public float dot(AbstractVector2f vec) {
		return x()*vec.x() + y()*vec.y();
	}

	@Override
	public final boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		AbstractVector2f other = (AbstractVector2f) obj;
		return Mathf.equals(x(), other.x()) && Mathf.equals(y(), other.y());
	}
	@Override
	public float get(int index) {
		return switch(index) {
			case 0 -> x();
			case 1 -> y();
			default ->
			throw new IllegalArgumentException("Unexpected value: " + index);
		};
	}

	@Override
	public final int hashCode() {
		return 31*(int)(x()/Mathf.EPS) + (int)(y()/Mathf.EPS);
	}

	@Override
	public float lengthSquared() {
		return x()*x() + y()*y();
	}

	public AbstractVector2f mul(AbstractVector2f vec) {
		return mul(vec.x(), vec.y());
	}

	@Override
	public AbstractVector2f mul(float f) {
		return mul(f, f, this);
	}

	public AbstractVector2f mul(float x, float y) {
		return mul(x, y, this);
	}

	public AbstractVector2f mul(float m11, float m12, float m21, float m22) {
		return mul(m11, m12, m21, m22, this);
	}
	public <T extends AbstractVector2f> T mul(float m00, float m01, float m10, float m11, T dest) {
		dest.set(m00 * x() + m10 * y(), m01 * x() + m11 * y());
		return dest;
	}

	public  <T extends AbstractVector2f> T mul(float x, float y, T dest) {
		dest.x(x() * x);
		dest.y(y() * y);
		return dest;
	}

	public <T extends AbstractVector2f> T mul(float f, T dest) {
		return mul(f, f, dest);
	}

	public AbstractVector2f mul(Matrix2f mat) {
		return mul(mat, this);
	}
	public <T extends AbstractVector2f> T mul(Matrix2f mat, T dest) {
		return mul(mat.m00(), mat.m01(), mat.m10(), mat.m11(), dest);
	}

	public <T extends AbstractVector2f> T normalize(float radius, T dest) {
		var len = length();
		if(len > 1E-9)
			return mul(radius / len, dest);
		return dest;
	}

	public <T extends AbstractVector2f> T normalize(T dest) {
		return normalize(1, dest);
	}
	public AbstractVector2f projection(AbstractVector2f norm) {
		return projection(norm, this);
	}
	public <T extends AbstractVector2f>  T projection(AbstractVector2f norm, T dest) {
		return norm.normalize(dot(norm), dest);
	}
	public AbstractVector2f set(AbstractVector2f vec) {
		return set(vec.x(), vec.y());
	}
	public void set(float f) {
		x(f);
		y(f);
	}
	public AbstractVector2f set(float x, float y) {
		x(x);
		y(y);
		return this;
	}

	@Override
	public void set(int index, float x) {
		switch(index) {
			case 0 -> x(x);
			case 1 -> y(x);
			default ->
			throw new IllegalArgumentException("Unexpected value: " + index);
		}
	}
	@Override
	public int size() {
		return 2;
	}

	public AbstractVector2f sub(AbstractVector2f vec) {
		return sub(vec.x(), vec.y());
	}
	public <T extends AbstractVector2f> T sub(AbstractVector2f vec, T dest) {
		return sub(vec.x(), vec.y(), dest);
	}

	public AbstractVector2f sub(float x, float y) {
		return sub(x, y, this);
	}
	public <T extends AbstractVector2f> T sub(float x, float y, T dest) {
		dest.x(x() - x);
		dest.y(y() - y);
		return dest;
	}
	public final org.joml.Vector2f toJoml() {
		return new org.joml.Vector2f(x(), y());
	}

	@Override
	public final String toString() {
		return "(" + x() + ", " + y() + ")";
	}
	public abstract float x();
	public abstract void x(float x);

	public AbstractVector2f xx() {
		return new Vector2f(x(), x());
	}
	public abstract float y();
	public abstract void y(float y);

	public AbstractVector2f yx() {
		return new Vector2f(y(), x());
	}

	public AbstractVector2f yy() {
		return new Vector2f(y(), y());
	}

}
