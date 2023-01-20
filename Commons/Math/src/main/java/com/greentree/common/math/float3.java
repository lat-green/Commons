package com.greentree.common.math;

import java.util.Objects;

import org.joml.Matrix4fc;

public class float3 {

	public float x, y, z;

	public float3() {
	}

	public float3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		float3 other = (float3) obj;
		if(Float.floatToIntBits(x) != Float.floatToIntBits(other.x) || Float.floatToIntBits(y) != Float.floatToIntBits(other.y) || Float.floatToIntBits(z) != Float.floatToIntBits(other.z)) return false;
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

	@Override
	public String toString() {
		return "float3 [" + x + ", " + y + ", " + z + "]";
	}

	public void mul(	float m00, float m01, float m02, float m03,
            			float m10, float m11, float m12, float m13,
            			float m20, float m21, float m22, float m23,
            			float m30, float m31, float m32, float m33) {

		final float x0 = m00*x+m10*y+m20*z+m30;
		final float y0 = m01*x+m11*y+m21*z+m31;
		final float z0 = m02*x+m12*y+m22*z+m32;
		final float w0 = m03*x+m13*y+m23*z+m33;
		
		this.x = x0/w0;
		this.y = y0/w0;
		this.z = z0/w0;
	}
	
	public void mul(Matrix4fc mat) {
		mul( 	mat.m00(), mat.m01(), mat.m02(), mat.m03(),
    			mat.m10(), mat.m11(), mat.m12(), mat.m13(),
    			mat.m20(), mat.m21(), mat.m22(), mat.m23(),
    			mat.m30(), mat.m31(), mat.m32(), mat.m33());
	}

}
