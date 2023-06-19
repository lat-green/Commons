package com.greentree.commons.geometry.geom2d.atomic;

import com.greentree.commons.geometry.geom2d.util.VectorGeometryUtil;
import com.greentree.commons.geometry.math.MathLine1D;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.FinalVector2f;
import com.greentree.commons.math.vector.Vector2f;
import com.greentree.commons.util.iterator.IteratorUtil;


public final class Box implements SimpleShape2D {
	
	public static final Box INSTANCE = new Box();
	
	private Box() {
	}
	
	private static final Iterable<? extends AbstractVector2f> NORMALS = IteratorUtil.iterable(
			new FinalVector2f(1, 0), new FinalVector2f(-1, 0), new FinalVector2f(0, 1),
			new FinalVector2f(0, -1));
	private static final Iterable<? extends AbstractVector2f> POINTS = IteratorUtil.iterable(
			new FinalVector2f(1, 1), new FinalVector2f(-1, 1), new FinalVector2f(1, -1),
			new FinalVector2f(-1, -1));
	
	@Override
	public boolean isInsade(AbstractVector2f point) {
		if(point.x() > 1)
			return false;
		if(point.x() < -1)
			return false;
		if(point.y() > 1)
			return false;
		if(point.y() < -1)
			return false;
		return true;
	}
	
	@Override
	public AbstractVector2f min(AbstractVector2f point) {
		var tx = (point.x() + 1) / 2;
		var ty = (point.y() + 1) / 2;
		
		tx = Mathf.clamp(0, 1, tx);
		ty = Mathf.clamp(0, 1, ty);
		
		var p = new Vector2f(2 * tx - 1, 2 * ty - 1);
		
		return p;
	}
	
	@Override
	public MathLine1D getProjection(AbstractVector2f normal) {
		var min = Float.MAX_VALUE;
		var max = Float.MIN_VALUE;
		for(var p : POINTS) {
			float v = VectorGeometryUtil.getProjectionPoint(p, normal);
			if(max < v)
				max = v;
			if(min > v)
				min = v;
		}
		return new MathLine1D(min, max);
	}
	
	@Override
	public Iterable<? extends AbstractVector2f> normals() {
		return NORMALS;
	}
	
	@Override
	public Iterable<? extends AbstractVector2f> points() {
		return POINTS;
	}
	
}
