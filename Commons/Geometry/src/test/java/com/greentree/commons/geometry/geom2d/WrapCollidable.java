package com.greentree.commons.geometry.geom2d;

import java.util.Objects;

import com.greentree.commons.geometry.geom2d.IShape2D;
import com.greentree.commons.geometry.geom2d.collision.Collidable2D;

public final class WrapCollidable implements Collidable2D {

	private final IShape2D shape;

	@Override
	public String toString() {
		return "WrapCollidable [" + shape + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(shape);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		WrapCollidable other = (WrapCollidable) obj;
		return Objects.equals(shape, other.shape);
	}

	public WrapCollidable(IShape2D shape) {
		this.shape = shape;
	}

	@Override
	public IShape2D getShape() {
		return shape;
	}



}