package com.greentree.commons.geometry.geom2d.shape;

import static com.greentree.commons.math.vector.AbstractVector2fKt.*;

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
	private final AbstractVector2f[] points;
	
	public Shape2D(Transform2D transform, AbstractVector2f... points) {
		
		this.transform = transform;
		
		final var c = VectorGeometryUtil.getCenter(points);
		origine_points = new FinalVector2f[points.length];
		for(int i = 0; i < points.length; i++) {
			origine_points[i] = new FinalVector2f(points[i].minus(c));
		}
		add(c);
		
		this.points = new AbstractVector2f[points.length];
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
	public AbstractVector2f[] getPoints() {
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
		
		var pos = transform.getPosition().minus(point).toMutable();
		
		final float x_ = pos.x(), y_ = pos.y();
		final float cos = Mathf.cos(angle);
		final float sin = Mathf.sin(angle);
		
		pos.set(x_ * cos + y_ * -sin, x_ * sin + y_ * cos);
		
		if(transform.setPosition(pos.plus(point)))
			update();
	}
	
	@Override
	public void scale(float x, float y) {
		final var s = transform.getScale().minus(vec2f(x, y));
		if(transform.setScale(s))
			update();
	}
	
}
