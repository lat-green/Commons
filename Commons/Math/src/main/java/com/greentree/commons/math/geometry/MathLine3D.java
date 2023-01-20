package com.greentree.commons.math.geometry;

import java.util.Objects;

import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.commons.math.vector.Vector3f;

public class MathLine3D {

	private Vector3f vector;
	private Vector3f point;

	public MathLine3D(AbstractVector3f point, AbstractVector3f vector) {
		this.point = new Vector3f(point);
		this.vector = new Vector3f(vector);
		if(this.vector.x() < 0)this.vector.mul(-1);
		this.vector.normalize();
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		MathLine3D other = (MathLine3D) obj;
		return Objects.equals(point, other.point) && Objects.equals(vector, other.vector);
	}

	@Override
	public int hashCode() {
		return Objects.hash(point, vector);
	}

	@Override
	public String toString() {
		return "MathLine3D [vector=" + vector + ", point=" + point + "]";
	}



}
