package com.greentree.common.math.vector.link;

import com.greentree.common.math.vector.AbstractVector3f;


public class LinkZ3f implements Link3f {

	public final static LinkZ3f LINK_Z = new LinkZ3f();

	private LinkZ3f() {
	}
	
	@Override
	public void set(AbstractVector3f vec, float z) {
		vec.z(z);
	}

	@Override
	public float get(AbstractVector3f vec) {
		return vec.z();
	}

}
