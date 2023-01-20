package com.greentree.commons.math.vector;

import java.util.Iterator;

import com.greentree.commons.math.Mathf;

public abstract class AbstractFloatVector extends AbstractVector implements Iterable<Float> {


	private static final long serialVersionUID = 1L;

	public AbstractFloatVector add(AbstractFloatVector v) {
		return add(v, this);
	}

	public <T extends AbstractFloatVector> T add(AbstractFloatVector v, T dest) {
		checkSize(dest);
		checkSize(v);
		for(int i = 0; i < v.size(); i++)
			dest.set(i, get(i) + v.get(i));
		return dest;
	}
	public float distance(AbstractFloatVector v) {
		return Mathf.sqrt(distanceSqr(v));
	}

	public float distanceSqr(AbstractFloatVector v) {
		checkSize(v);
		final var s = new VectorNf(size());
		sub(v, s);
		return s.lengthSquared();
	}

	public float dot(AbstractFloatVector v) {
		checkSize(v);
		var res = 0f;
		for(int i = 0; i < size(); i++)
			res += v.get(i) * get(i);
		return res;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		var other = (AbstractVectorNf) obj;
		if(size() != other.size()) return false;
		for(int i = 0; i < size(); i++)
			if(Mathf.equals(get(i), other.get(i)))
				return false;
		return true;
	}
	public abstract float get(int index);

	@Override
	public int hashCode() {
		var res = 1f;
		for(int i = 0; i < size(); i++)
			res = 31 * res + get(i);
		return Float.floatToIntBits(res);
	}

	@Override
	public Iterator<Float> iterator() {
		return new AbstractFloatVectorIterator();
	}

	@Override
	public float lengthSquared() {
		var lengthSquared = 0.0f;
		for(int i = 0; i < size(); i++) {
			final var x = get(i);
			lengthSquared += x * x;
		}
		return lengthSquared;
	}

	public AbstractFloatVector mul(AbstractFloatVector v) {
		return mul(v, this);
	}

	public <T extends AbstractFloatVector> T mul(AbstractFloatVector v, T dest) {
		checkSize(dest);
		checkSize(v);
		for(int i = 0; i < v.size(); i++)
			dest.set(i, get(i) * v.get(i));
		return dest;
	}

	public AbstractFloatVector mul(float k) {
		return mul(k, this);
	}

	public <T extends AbstractFloatVector> T mul(float k, T dest) {
		checkSize(dest);
		for(int i = 0; i < size(); i++)
			dest.set(i, k * get(i));
		return dest;
	}

	public AbstractFloatVector normalize() {
		return normalize(1, this);
	}

	public AbstractFloatVector normalize(float radius) {
		return normalize(radius, this);
	}
	public <T extends AbstractFloatVector> T normalize(float radius, T dest) {
		var len = length();
		if(len > 1E-9)
			return mul(radius / len, dest);
		return dest;
	}

	public <T extends AbstractFloatVector> T normalize(T dest) {
		return normalize(1, dest);
	}

	public AbstractFloatVector projection(AbstractFloatVector norm) {
		return projection(norm, this);
	}

	public <T extends AbstractFloatVector>  T projection(AbstractFloatVector norm, T dest) {
		checkSize(norm);
		checkSize(dest);
		return norm.normalize(dot(norm), dest);
	}

	public abstract void set(int index, float x);

	public AbstractFloatVector sub(AbstractFloatVector v) {
		return sub(v, this);
	}

	public <T extends AbstractFloatVector> T sub(AbstractFloatVector v, T dest) {
		checkSize(dest);
		checkSize(v);
		for(int i = 0; i < v.size(); i++)
			dest.set(i, get(i) - v.get(i));
		return dest;
	}

	public final class AbstractFloatVectorIterator implements Iterator<Float> {

		private int index = 0;

		@Override
		public boolean hasNext() {
			return index < size();
		}

		@Override
		public Float next() {
			return get(index++);
		}

	}

}
