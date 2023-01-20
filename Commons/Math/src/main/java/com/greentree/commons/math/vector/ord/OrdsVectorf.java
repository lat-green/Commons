package com.greentree.commons.math.vector.ord;

import com.greentree.commons.math.vector.AbstractVectorNf;

/**
 * @author Arseny Latyshev
 *
 */
public class OrdsVectorf extends AbstractVectorNf {
	private static final long serialVersionUID = 1L;

	private final FloatVectorOrd[] ords;
	
	@SafeVarargs
	public OrdsVectorf(FloatVectorOrd...ords) {
		this.ords = ords.clone();
	}

	@Override
	public float get(int index) {
		return ords[index].get();
	}

	@Override
	public void set(int index, float x) {
		final var ord = ords[index];
		if(ord instanceof ChangeableFloatVectorOrd)
			((ChangeableFloatVectorOrd) ord).set(x);
		else
			throw new UnsupportedOperationException("change of " + this);
	}

	@Override
	public int size() {
		return ords.length;
	}

}
