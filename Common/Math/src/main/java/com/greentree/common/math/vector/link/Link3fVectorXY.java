package com.greentree.common.math.vector.link;

import com.greentree.common.math.vector.AbstractVector3f;

public class Link3fVectorXY extends Link3fVector2 {
	private static final long serialVersionUID = 1L;

	public Link3fVectorXY(AbstractVector3f original) {
		super(LinkX3f.LINK_X, LinkY3f.LINK_Y, original);
	}

}
