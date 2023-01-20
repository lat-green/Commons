package com.greentree.commons.geometry.geom3d.operation;

import com.greentree.commons.geometry.geom3d.IShape3D;
import com.greentree.commons.geometry.geom3d.Shape3DUtil;
import com.greentree.commons.geometry.geom3d.collision.CollisionEvent3D;

public abstract class Shape3DBinaryOperation<A extends IShape3D, B extends IShape3D> {

	public CollisionEvent3D.Builder getCollisionEvent(A a, B b) {
		//		Vector3f normal = Shape3DUtil.getCollisionNormalOnNormalProjection(a, b);
		//		return new CollisionEvent3D.Builder(Shape3DUtil.getCollisionPoint(a, b), normal, Shape3DUtil.getProjectionOverlay(a, b, new Vector2f(normal.y, -normal.x)));
		return null;
	}

	public boolean isIntersect(final A a, final B b) {
		if(!a.getAABB().isIntersect(b.getAABB())) return false;
		return Shape3DUtil.isIntersectOnNormalProjection(a, b);
	}

}
