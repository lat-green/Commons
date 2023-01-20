package com.greentree.commons.math.vector.link;

import com.greentree.commons.math.vector.AbstractVector2f;


public class LinkX2f implements Link2f {

	public final static LinkX2f LINK_X = new LinkX2f();
	
	private LinkX2f() {
	}
	
	@Override
	public void set(AbstractVector2f vec, float x) {
		vec.x(x);
	}

	@Override
	public float get(AbstractVector2f vec) {
		return vec.x();
	}

}
