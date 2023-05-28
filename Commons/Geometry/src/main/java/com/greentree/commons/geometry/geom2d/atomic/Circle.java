package com.greentree.commons.geometry.geom2d.atomic;

import com.greentree.commons.geometry.math.MathLine1D;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.Vector2f;
import com.greentree.commons.util.collection.InfinityCollection;


public class Circle implements SimpleShape2D {
	
	public static final Circle INSTANCE = new Circle();
	
	private Circle() {
	}
	
	@Override
	public boolean isInsade(AbstractVector2f point) {
		return point.lengthSquared() <= 1;
	}
	
	@Override
	public AbstractVector2f min(AbstractVector2f point) {
		return new Vector2f(point).normalize();
	}
	
	@Override
	public MathLine1D getProjection(AbstractVector2f normal) {
		return new MathLine1D(-1, 1);
	}
	
	@Override
	public Iterable<? extends AbstractVector2f> normals() {
		return new InfinityCollection<>();
	}
	
	@Override
	public Iterable<? extends AbstractVector2f> points() {
		return new InfinityCollection<>();
	}
	
}
