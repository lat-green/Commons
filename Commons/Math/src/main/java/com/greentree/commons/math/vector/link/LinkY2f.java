package com.greentree.commons.math.vector.link;

import com.greentree.commons.math.vector.AbstractVector2f;


public class LinkY2f implements Link2f {

	public final static LinkY2f LINK_Y = new LinkY2f();

	private LinkY2f() {
	}
	
	@Override
	public void set(AbstractVector2f vec, float y) {
		vec.y(y);
	}

	@Override
	public float get(AbstractVector2f vec) {
		return vec.y();
	}

}
