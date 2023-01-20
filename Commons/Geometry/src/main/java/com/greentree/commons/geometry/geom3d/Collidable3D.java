package com.greentree.commons.geometry.geom3d;

import com.greentree.commons.geometry.geom3d.face.Face;
import com.greentree.commons.math.vector.AbstractVector3f;

public interface Collidable3D extends IShape3D {
	
	@Override
	default float distanceSquared(AbstractVector3f p) {
		return getShape().distanceSquared(p);
	}
	
	@Override
	default AABB getAABB() {
		return getShape().getAABB();
	}
	
	@Override
	default float getArea() {
		return getShape().getArea();
	}
	
	@Override
	default AbstractVector3f getCenter() {
		return getShape().getCenter();
	}
	
	@Override
	default Face[] getFaces() {
		return getShape().getFaces();
	}
	
	IShape3D getShape();
	
	@Override
	default boolean isIntersect(IShape3D other) {
		return getShape().isIntersect(other);
	}
	
	
}
