package com.greentree.commons.math.vector.link;

import com.greentree.commons.math.vector.AbstractVector3f;


public class LinkY3f implements Link3f {

	public final static LinkY3f LINK_Y = new LinkY3f();

	private LinkY3f() {
	}
	
	@Override
	public void set(AbstractVector3f vec, float y) {
		vec.y(y);
	}

	@Override
	public float get(AbstractVector3f vec) {
		return vec.y();
	}

}
