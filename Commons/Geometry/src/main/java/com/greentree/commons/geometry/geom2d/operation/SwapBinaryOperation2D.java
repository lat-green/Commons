package com.greentree.commons.geometry.geom2d.operation;

import java.util.Collection;

import com.greentree.commons.geometry.geom2d.IShape2D;
import com.greentree.commons.geometry.geom2d.collision.CollisionEvent2D;
import com.greentree.commons.math.vector.AbstractVector2f;


public final class SwapBinaryOperation2D<A extends IShape2D, B extends IShape2D>
		extends Shape2DBinaryOperation<A, B> {
	
	private final Shape2DBinaryOperation<B, A> shape2DBinaryOperation;
	
	public SwapBinaryOperation2D(Shape2DBinaryOperation<B, A> collisionHendler2D) {
		this.shape2DBinaryOperation = collisionHendler2D;
	}
	
	@Override
	public CollisionEvent2D.Builder getCollisionEvent(A a, B b) {
		var e = shape2DBinaryOperation.getCollisionEvent(b, a);
		return e.inverse();
	}
	
	@Override
	public Collection<AbstractVector2f> getContactPoint(A a, B b) {
		return shape2DBinaryOperation.getContactPoint(b, a);
	}
	
	@Override
	public boolean isIntersect(A a, B b) {
		return shape2DBinaryOperation.isIntersect(b, a);
	}
	
	@Override
	public String toString() {
		return "SwapBinaryOperation2D [shape2DBinaryOperation=" + shape2DBinaryOperation + "]";
	}
	
}
