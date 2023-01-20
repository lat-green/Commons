package com.greentree.commons.geometry.geom2d.util;

import com.greentree.commons.geometry.geom2d.shape.Transform2D;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.Vector2f;


public final class Transform2DImpl implements Transform2D {
	
	private final AbstractVector2f position, scale;
	private float rotation;
	
	public Transform2DImpl() {
		this(0, 0);
	}
	
	public Transform2DImpl(float x, float y) {
		position = new Vector2f(x, y);
		scale = new Vector2f(1);
		rotation = 0;
	}
	
	@Override
	public AbstractVector2f getPosition() {
		return position;
	}
	
	
	@Override
	public float getRotation() {
		return rotation;
	}
	
	@Override
	public AbstractVector2f getScale() {
		return scale;
	}
	
	@Override
	public boolean setPosition(float x, float y) {
		position.set(x, y);
		return true;
	}
	
	@Override
	public boolean setRotation(double rotation) {
		this.rotation = (float) rotation;
		return true;
	}
	
	@Override
	public boolean setScale(float x, float y) {
		scale.set(x, y);
		return true;
	}
	
	@Override
	public String toString() {
		return "BasicGeom2DTransform [position=" + position + ", scale=" + scale + ", rotation="
				+ rotation + "]";
	}
	
}
