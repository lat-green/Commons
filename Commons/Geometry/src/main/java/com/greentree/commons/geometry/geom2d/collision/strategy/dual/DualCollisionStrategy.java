package com.greentree.commons.geometry.geom2d.collision.strategy.dual;

import java.util.Collection;

import com.greentree.commons.geometry.geom2d.collision.Collidable2D;
import com.greentree.commons.util.cortege.UnOrentetPair;

public interface DualCollisionStrategy<T extends Collidable2D> {
	
	Collection<? extends UnOrentetPair<T>> getCollisionContact(Collection<T> a, Collection<T> b);
	
}
