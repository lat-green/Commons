package com.greentree.common.math.vector;

import java.util.Objects;

import org.joml.Matrix3f;
import org.joml.Matrix4fc;

import com.greentree.common.math.Mathf;

public abstract class AbstractVector3f extends AbstractFloatVector {

	private static final long serialVersionUID = 1L;

	public static final AbstractVector3f X = new FinalVector3f(1, 0, 0);
	public static final AbstractVector3f Y = new FinalVector3f(0, 1, 0);
	public static final AbstractVector3f Z = new FinalVector3f(0, 0, 1);
	
	@Override
	public float get(int index) {
		return switch(index) {
			case 0 -> x();
			case 1 -> y();
			case 2 -> z();
			default ->
				throw new IllegalArgumentException("Unexpected value: " + index);
		};
	}
	
	@Override
	public void set(int index, float x) {
		switch(index) {
			case 0 -> x(x);
			case 1 -> y(x);
			case 2 -> z(x);
			default ->
				throw new IllegalArgumentException("Unexpected value: " + index);
		}
	}
    
	@Override
    public int size() {
    	return 3;
    }
	
	public AbstractVector3f() {
	}

	public AbstractVector3f(AbstractVector2f f) {
		this(f, 0);
	}
	public AbstractVector3f(AbstractVector2f f, float z) {
		set(f.x(), f.y(), z);
	}

	public AbstractVector3f(AbstractVector3f f) {
		set(f, this);
	}
	public AbstractVector3f(float f) {
		set(f, f, f);
	}
	public AbstractVector3f(float x, float y, float z) {
		set(x, y, z);
	}
	public AbstractVector3f add(AbstractVector3f vec) {
		return add(vec, this);
	}
	public <T extends AbstractVector3f> T add(AbstractVector3f vec, T dest) {
		return add(vec.x(), vec.y(), vec.z(), dest);
	}
	public AbstractVector3f add(float x, float y, float z) {
		return add(x, y, z, this);
	}

	public <T extends AbstractVector3f> T add(float x, float y, float z, T dest) {
		dest.x(x() + x);
		dest.y(y() + y);
		dest.z(z() + z);
		return dest;
	}
	public void addXY(AbstractVector2f vec) {
		add(vec.x(), vec.y(), 0);
	}
	public AbstractVector3f cross(AbstractVector3f vec) {
		return cross(vec, this);
	}
	public <T extends AbstractVector3f> T cross(AbstractVector3f vec, T dest) {
		dest.set(y()*vec.z() - z()*vec.y(), z()*vec.x() - x()*vec.z(), x()*vec.y() - y()*vec.x());
		return dest;
	}
	public final float distance(float x, float y, float z) {
		return Mathf.sqrt(distanceSquared(x, y, z));
	}
	public float distanceSquared(AbstractVector3f point) {
		return distanceSquared(point.x(), point.y(), point.z());
	}

	public float distanceSquared(float x, float y, float z) {
		float dx = x - x();
		float dy = y - y();
		float dz = z - z();
		return dx*dx+dy*dy+dz*dz;
	}

	public AbstractVector3f div(float x, float y, float z) {
		return div(x, y, z, this);
	}
	public AbstractVector3f div(float x, float y, float z, AbstractVector3f dest) {
		dest.x(x() / x);
		dest.y(y() / y);
		dest.z(z() / z);
		return dest;
	}
	public float dot(AbstractVector3f vec) {
		return vec.x() * x() + vec.y() * y() + vec.z() * z();
	}

	@Override
	public final boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof AbstractVector3f)) return false;
		AbstractVector3f other = (AbstractVector3f) obj;
		return Mathf.equals(x(), other.x()) && Mathf.equals(y(), other.y()) && Mathf.equals(z(), other.z());
	}


	public AbstractVector2f getProjection(AbstractVector3f normal) {
		float a = normal.x(), b = normal.y(), c = normal.z();
		Vector3f xAxis;
		Vector3f yAxis = new Vector3f(a,b,c).normalize();
		{
			xAxis = new Vector3f(b, -a, 0);
			if(xAxis.lengthSquared() < Mathf.EPS)
				xAxis = new Vector3f(0, c, -b);
		}
		yAxis.cross(xAxis);

		float x = dot(xAxis);
		float y = dot(yAxis);

		//	    System.out.println(x + "  " + y + " " + this + " " + xAxis + " " + yAxis + " " + normal);

		return new Vector2f(x, y);
	}
	@Override
	public final int hashCode() {
		return Objects.hash((int)(x() / Mathf.EPS), (int)(y() / Mathf.EPS), (int)(z() / Mathf.EPS));
	}

	@Override
	public float lengthSquared() {
		return x()*x() + y()*y() + z()*z();
	}

	public <T extends AbstractVector3f> T lerp(AbstractVector3f v, float k) {
		return lerp(v.x(), v.y(), v.z(), k);
	}

	public <T extends AbstractVector3f> T lerp(AbstractVector3f v, float k, T dest) {
		return lerp(v.x(), v.y(), v.z(), k, dest);
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractVector3f> T lerp(float x, float y, float z, float k) {
		return (T) lerp(x,y,z,k,this);
	}

	public <T extends AbstractVector3f> T lerp(float x, float y, float z, float k, T dest) {
		dest.x(Mathf.lerp(x(), x, k));
		dest.y(Mathf.lerp(y(), y, k));
		dest.z(Mathf.lerp(z(), z, k));
		return dest;
	}

	public <T extends AbstractVector3f> T mul(AbstractVector3f v, T dest) {
		return mul(v.x(), v.y(), v.z(), dest);
	}
	public AbstractVector3f mul(float f) {
		return mul(f, this);
	}
	public AbstractVector3f mul(float x, float y, float z) {
		return mul(x, y, z, this);
	}
	public AbstractVector3f mul(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
		return mul( m00,  m01,  m02, m10,  m11,  m12,   m20,  m21,  m22,  this);
	}
	public AbstractVector3f mul(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30,
			float m31, float m32, float m33) {
		return mul( m00,  m01,  m02,  m03,  m10,  m11,  m12,  m13,  m20,  m21,  m22,  m23,  m30,
				m31,  m32,  m33, this);
	}

	public <T extends AbstractVector3f> T mul(	float m00, float m01, float m02, float m03,
			float m10, float m11, float m12, float m13,
			float m20, float m21, float m22, float m23,
			float m30, float m31, float m32, float m33, T dest) {

		final float x = m00*x()+m10*y()+m20*z()+m30;
		final float y = m01*x()+m11*y()+m21*z()+m31;
		final float z = m02*x()+m12*y()+m22*z()+m32;
		final float w = m03*x()+m13*y()+m23*z()+m33;

		//		float x = m00*x()+m01*y()+m02*z()+m03;
		//		float y = m10*x()+m11*y()+m12*z()+m13;
		//		float z = m20*x()+m21*y()+m22*z()+m23;
		//		float w = m30*x()+m31*y()+m32*z()+m33;

		dest.set(x, y, z);
		dest.mul(1f / w);

		return dest;
	}
	@SuppressWarnings("unchecked")
	public <T extends AbstractVector3f> T mul(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22, T dest) {
		return (T) dest.set(m00*x()+m10*y()+m20*z(),
				m01*x()+m11*y()+m21*z(),
				m02*x()+m12*y()+m22*z());
	}
	public <T extends AbstractVector3f> T mul(float x, float y, float z, T dest) {
		dest.x(x() * x);
		dest.y(y() * y);
		dest.z(z() * z);
		return dest;
	}
	public <T extends AbstractVector3f> T mul(float f, T dest) {
		return mul(f, f, f, dest);
	}
	public AbstractVector3f mul(Matrix3f mat) {
		return mul(mat, this);
	}
	public <T extends AbstractVector3f> T mul(Matrix3f mat, T dest) {
		return mul(		mat.m00(), mat.m01(), mat.m02(),
				mat.m10(), mat.m11(), mat.m12(),
				mat.m20(), mat.m21(), mat.m22(),  dest);
	}

	public AbstractVector3f mul(Matrix4fc mat) {
		return mul(mat, this);
	}

	public AbstractVector3f mul(Matrix4fc mat, AbstractVector3f dest) {
		return mul( mat.m00(), mat.m01(), mat.m02(), mat.m03(),
				mat.m10(), mat.m11(), mat.m12(), mat.m13(),
				mat.m20(), mat.m21(), mat.m22(), mat.m23(),
				mat.m30(), mat.m31(), mat.m32(), mat.m33(), dest);
	}
	public AbstractVector3f normalize() {
		return normalize(1, this);
	}
	public AbstractVector3f normalize(AbstractVector3f dest) {
		return normalize(1, dest);
	}
	public AbstractVector3f normalize(float radius) {
		return mul(radius / length(), this);
	}

	public <T extends AbstractVector3f> T normalize(float radius, T dest) {
		return mul(radius / length(), dest);
	}

	public AbstractVector3f set(AbstractVector2f vec, float z) {
		return set(vec, z, this);
	}
	public AbstractVector3f set(AbstractVector2f vec, float z, AbstractVector3f dest) {
		return dest.set(vec.x(), vec.y(), z);
	}
	public AbstractVector3f set(AbstractVector3f vec) {
		return set(vec.x(), vec.y(), vec.z());
	}
	public AbstractVector3f set(AbstractVector3f vec, AbstractVector3f dest) {
		return dest.set(vec.x(), vec.y(), vec.z());
	}
	public AbstractVector3f set(float f) {
		return set(f, this);
	}
	public AbstractVector3f set(float f, AbstractVector3f dest) {
		return dest.set(f, f, f);
	}
	public AbstractVector3f set(float x, float y, float z) {
		x(x);
		y(y);
		z(z);
		return this;
	}

	public abstract void x(float x);
	public abstract void y(float y);
	public abstract void z(float z);
	public AbstractVector3f sub(AbstractVector3f vec) {
		return sub(vec, this);
	}

	public AbstractVector3f sub(AbstractVector3f vec, AbstractVector3f dest) {
		return sub(vec.x(), vec.y(), vec.z(), dest);
	}

	public AbstractVector3f sub(float x, float y, float z) {
		return sub(x, y, z, this);
	}

	public AbstractVector3f sub(float x, float y, float z, AbstractVector3f dest) {
		dest.x(x() - x);
		dest.y(y() - y);
		dest.z(z() - z);
		return dest;
	}

	public org.joml.Vector3f toJoml() {
		return new org.joml.Vector3f(x(), y(), z());
	}

	@Override
	public String toString() {
		return "(" + x() + ", " + y() + ", " + z() + ")";
	}
	public abstract float x();

	public Vector2f xx() {
		return new Vector2f(x(), x());
	}

	public AbstractVector3f xxx() {
		return new Vector3f(x(), x(), x());
	}

	public AbstractVector3f xxy() {
		return new Vector3f(x(), x(), y());
	}
	public AbstractVector3f xxz() {
		return new Vector3f(x(), x(), z());
	}

	public Vector2f xy() {
		return new Vector2f(x(), y());
	}
	public <T extends AbstractVector2f> T xy(T dest) {
		dest.x(x());
		dest.y(y());
		return dest;
	}

	public AbstractVector3f xyx() {
		return new Vector3f(x(), y(), x());
	}

	public AbstractVector3f xyy() {
		return new Vector3f(x(), y(), y());
	}
	public AbstractVector2f xz() {
		return new Vector2f(x(), z());
	}
	public AbstractVector3f xzx() {
		return new Vector3f(x(), z(), x());
	}

	public AbstractVector3f xzy() {
		return new Vector3f(x(), z(), y());
	}
	public AbstractVector3f xzz() {
		return new Vector3f(x(), z(), z());
	}

	public abstract float y();
	public AbstractVector2f yx() {
		return new Vector2f(y(), x());
	}
	public AbstractVector3f yxx() {
		return new Vector3f(y(), x(), x());
	}
	public AbstractVector3f yxy() {
		return new Vector3f(y(), x(), y());
	}

	public AbstractVector3f yxz() {
		return new Vector3f(y(), x(), z());
	}
	public AbstractVector2f yy() {
		return new Vector2f(y(), y());
	}
	public AbstractVector3f yyx() {
		return new Vector3f(y(), y(), x());
	}


	public AbstractVector3f yyy() {
		return new Vector3f(y(), y(), y());
	}

	public AbstractVector3f yyz() {
		return new Vector3f(y(), y(), z());
	}

	public AbstractVector2f yz() {
		return new Vector2f(y(), z());
	}

	public AbstractVector3f yzx() {
		return new Vector3f(y(), z(), x());
	}

	public AbstractVector3f yzy() {
		return new Vector3f(y(), z(), y());
	}
	public AbstractVector3f yzz() {
		return new Vector3f(y(), z(), z());
	}

	public abstract float z();
	public AbstractVector2f zx() {
		return new Vector2f(z(), x());
	}

	public AbstractVector3f zxx() {
		return new Vector3f(z(), x(), x());
	}
	public AbstractVector3f zxy() {
		return new Vector3f(z(), x(), y());
	}

	public AbstractVector3f zxz() {
		return new Vector3f(z(), x(), z());
	}
	public AbstractVector2f zy() {
		return new Vector2f(z(), y());
	}
	public AbstractVector3f zyx() {
		return new Vector3f(z(), y(), x());
	}
	public AbstractVector3f zyy() {
		return new Vector3f(z(), y(), y());
	}

	public AbstractVector3f zyz() {
		return new Vector3f(z(), y(), z());
	}

	public AbstractVector2f zz() {
		return new Vector2f(z(), z());
	}

	public AbstractVector3f zzx() {
		return new Vector3f(z(), z(), x());
	}

	public AbstractVector3f zzy() {
		return new Vector3f(z(), z(), y());
	}

	public AbstractVector3f zzz() {
		return new Vector3f(z(), z(), z());
	}

}
