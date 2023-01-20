package com.greentree.commons.geometry.geom3d.face;

import com.greentree.commons.math.vector.AbstractVector3f;


public class BasicFace implements Face {
	
	private final AbstractVector3f p1, p2, p3;
	
	public BasicFace(AbstractVector3f p1, AbstractVector3f p2, AbstractVector3f p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}
	
	@Override
	public AbstractVector3f getP1() {
		return p1;
	}
	
	@Override
	public AbstractVector3f getP2() {
		return p2;
	}
	
	@Override
	public AbstractVector3f getP3() {
		return p3;
	}
	
}
