package com.greentree.commons.geometry.geom2d.shape;

import com.greentree.commons.geometry.geom2d.util.Transform2DImpl;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.util.iterator.IteratorUtil;


public class Poligon2D extends Shape2D {
	
	public Poligon2D(final AbstractVector2f... point) {
		super(new Transform2DImpl(), point);
	}
	
	public Poligon2D(Iterable<? extends AbstractVector2f> point) {
		this(IteratorUtil.array(point));
	}
	
}
