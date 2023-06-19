package com.greentree.commons.geometry.geom2d.collision.strategy;

import java.util.Collection;

import com.greentree.commons.geometry.geom2d.Shape2DUtil;
import com.greentree.commons.geometry.geom2d.collision.Collidable2D;
import com.greentree.commons.geometry.geom2d.collision.CollisionEvent2D;
import com.greentree.commons.util.cortege.Pair;


public abstract class AbstractCollisionStrategy extends CollisionStrategy {
	
	public abstract <T extends Collidable2D> Collection<Pair<T, T>> getCollisionContact(T[] world);
	
	@SafeVarargs
	@Override
	public final <T extends Collidable2D> Collection<CollisionEvent2D<T, T>> getCollisions(
			T... world) {
		return Shape2DUtil.getCollisions(getCollisionContact(world));
	}
	
	
}
