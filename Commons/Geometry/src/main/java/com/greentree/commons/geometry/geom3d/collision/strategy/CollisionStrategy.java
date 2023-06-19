package com.greentree.commons.geometry.geom3d.collision.strategy;

import java.util.Collection;

import com.greentree.commons.geometry.geom3d.Collidable3D;
import com.greentree.commons.geometry.geom3d.collision.CollisionEvent3D;

public abstract class CollisionStrategy {

	public abstract <T extends Collidable3D> Collection<CollisionEvent3D<T, T>> getCollisions(@SuppressWarnings("unchecked") T...world);

}
