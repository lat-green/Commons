package com.greentree.commons.geometry.geom3d.operation;

import com.greentree.commons.geometry.geom3d.IShape3D;
import com.greentree.commons.geometry.geom3d.collision.CollisionEvent3D;


public final class SwapBinaryOperation3D<A extends IShape3D, B extends IShape3D> extends Shape3DBinaryOperation<A, B> {

	private final Shape3DBinaryOperation<B, A> shape2DBinaryOperation;

	public SwapBinaryOperation3D(Shape3DBinaryOperation<B, A> collisionHendler2D) {
		if(collisionHendler2D instanceof SwapBinaryOperation3D) throw new IllegalArgumentException();
		this.shape2DBinaryOperation = collisionHendler2D;
	}

	@Override
	public CollisionEvent3D.Builder getCollisionEvent(A a, B b) {
		var e = shape2DBinaryOperation.getCollisionEvent(b, a);
		e.normal.mul(-1);
		return e;
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
