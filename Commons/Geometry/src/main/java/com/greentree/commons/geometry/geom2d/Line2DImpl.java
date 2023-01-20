package com.greentree.commons.geometry.geom2d;

import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.FinalVector2f;
import com.greentree.commons.math.vector.Vector2f;


public class Line2DImpl implements ILine2D {
	
	private final FinalVector2f p1, p2;
	
	@Override
	public String toString() {
		return "Line2DImpl [" + p1 + ", " + p2 + "]";
	}
	
	public Line2DImpl(final AbstractVector2f p1, final AbstractVector2f p2) {
		this.p1 = new FinalVector2f(p1);
		this.p2 = new FinalVector2f(p2);
	}
	
	public Line2DImpl(final float x1, final float y1, final float x2, final float y2) {
		this(new Vector2f(x1, y1), new Vector2f(x2, y2));
	}
	
	@Override
	public FinalVector2f p1() {
		return p1;
	}
	
	@Override
	public FinalVector2f p2() {
		return p2;
	}
	
}
