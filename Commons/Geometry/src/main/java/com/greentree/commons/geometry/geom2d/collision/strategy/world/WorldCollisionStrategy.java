package com.greentree.commons.geometry.geom2d.collision.strategy.world;

import java.util.ArrayList;
import java.util.Collection;

import com.greentree.commons.geometry.geom2d.Shape2DUtil;
import com.greentree.commons.geometry.geom2d.collision.Collidable2D;
import com.greentree.commons.geometry.geom2d.collision.CollisionEvent2D;
import com.greentree.commons.util.cortege.Pair;

public abstract class WorldCollisionStrategy<T extends Collidable2D> {
	
	public WorldCollisionStrategy() {
	}
	
	public WorldCollisionStrategy(Iterable<T> iterable) {
		for(var s : iterable)
			add(s);
	}
	
	public WorldCollisionStrategy(T... array) {
		for(var s : array)
			add(s);
	}
	
	public final void add(T shape) {
		add0(shape);
	}
	
	public abstract Collection<? extends Pair<T, T>> getCollisionContact();
	
	public final Collection<CollisionEvent2D<T, T>> getCollisions() {
		Collection<? extends Pair<T, T>> contacts = getCollisionContact();
		Collection<CollisionEvent2D<T, T>> res = new ArrayList<>(contacts.size());
		for(var pair : contacts)
			res.add(Shape2DUtil.getCollisionEvent(pair.first, pair.seconde));
		return res;
	}
	
	public final void remove(T shape) {
		remove0(shape);
	}
	
	protected abstract void add0(T shape);
	
	protected abstract void remove0(T shape);
	
	protected void reset0(T shape) {
		remove0(shape);
		add0(shape);
	}
}
