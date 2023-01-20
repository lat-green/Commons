package com.greentree.common.math.vector.link;

import com.greentree.common.math.vector.AbstractVector3f;


public class LinkX3f implements Link3f {

	public final static LinkX3f LINK_X = new LinkX3f();
	
	private LinkX3f() {
	}
	
	@Override
	public void set(AbstractVector3f vec, float x) {
		vec.x(x);
	}

	@Override
	public float get(AbstractVector3f vec) {
		return vec.x();
	}

}
