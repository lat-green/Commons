package com.greentree.commons.geometry.geom2d.shape;

import java.util.Arrays;

import com.greentree.commons.geometry.geom2d.IMovableShape2D;
import com.greentree.commons.geometry.geom2d.util.VectorGeometryUtil;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.FinalVector2f;
import com.greentree.commons.math.vector.Vector2f;


public abstract class Shape2D implements IMovableShape2D {
	
	private transient boolean should_update;
	private final Transform2D transform;
	private final FinalVector2f[] origine_points;
	private final Vector2f[] points;
	
	public Shape2D(Transform2D transform, AbstractVector2f... points) {
		
		this.transform = transform;
		
		final var c = VectorGeometryUtil.getCenter(points);
		origine_points = new FinalVector2f[points.length];
		for(int i = 0; i < points.length; i++) {
			final var p = new Vector2f(points[i]);
			p.sub(c);
			origine_points[i] = new FinalVector2f(p);
		}
		add(c);
		
		this.points = new Vector2f[points.length];
		for(int i = 0; i < points.length; i++)
			this.points[i] = new Vector2f();
		should_update = true;
	}
	
	protected final void update() {
		should_update = true;
	}
	
	protected final void load() {
		if(should_update) {
			should_update = false;
			transform.get(origine_points, Shape2D.this.points);
		}
	}
	
	@Override
	public AbstractVector2f getCenter() {
		return transform.getPosition();
	}
	
	@Override
	public Vector2f[] getPoints() {
		load();
		return points;
	}
	
	@Override
	public int getPointsCount() {
		return origine_points.length;
	}
	
	public Transform2D getTransform() {
		return transform;
	}
	
	@Override
	public final void rotate(double angle) {
		final var rot = transform.getRotation();
		if(transform.setRotation(angle + rot))
			update();
	}
	
	@Override
	public final String toString() {
		return getClass().getSimpleName() + "[" + Arrays.toString(getPoints()) + "]";
	}
	
	@Override
	public final void add(float x, float y) {
		final var pos = transform.getPosition();
		if(transform.setPosition(pos.x() + x, pos.y() + y))
			update();
	}
	
	@Override
	public final void rotate(AbstractVector2f point, double angle) {
		final var rot = transform.getRotation();
		if(transform.setRotation(angle + rot))
			update();
		
		var pos = transform.getPosition();
		
		final float x_ = pos.x(), y_ = pos.y();
		final float cos = Mathf.cos(angle);
		final float sin = Mathf.sin(angle);
		
		pos.sub(point).set(x_ * cos + y_ * -sin, x_ * sin + y_ * cos).add(point);
		
		if(transform.setPosition(pos))
			update();
	}
	
	@Override
	public void scale(float x, float y) {
		final var s = transform.getScale();
		s.mul(x, y);
		if(transform.setScale(s))
			update();
	}
	
}
