package com.greentree.commons.geometry.geom2d.collision;

import java.util.Collection;

import com.greentree.commons.util.cortege.Pair;

public interface WorldLayerCollisionStrategy<T extends Collidable2D> {
	
	void add(int layer, T shape);
	
	boolean contains(T shape);
	Collection<Pair<T, T>> getCollisionContacts();
	boolean isCollisionLayers(int a, int b);
	Collection<Integer> layers();
	
	void remove(T shape);
	
	
}
