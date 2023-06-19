package com.greentree.commons.geometry.geom2d.collision.strategy.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import com.greentree.commons.geometry.geom2d.Shape2DUtil;
import com.greentree.commons.geometry.geom2d.collision.Collidable2D;
import com.greentree.commons.util.cortege.Pair;


public class BruteWorldCollisionStrategy<T extends Collidable2D> extends WorldCollisionStrategy<T> {
	
	private static final Comparator<Collidable2D> comparator = Comparator
			.comparing(c->c.getShape().getAABB().getMinX());
	private Collidable2D[] world = new Collidable2D[10];
	private int size = 0;
	
	@Override
	public Collection<Pair<T, T>> getCollisionContact() {
		final Collection<Pair<T, T>> res = new ArrayList<>();
		for(int i = size - 1; i >= 0; i--) {
			T a = (T) world[i];
			final var sa = a.getShape();
			var aAABB = sa.getAABB();
			for(int j = i + 1; j < size; j++) {
				T b = (T) world[j];
				final var sb = b.getShape();
				if(Shape2DUtil.isIntersect(sa, sb))
					res.add(new Pair<>(a, b));
			}
		}
		
		return res;
	}
	
	@Override
	protected void add0(T shape) {
		if(world.length <= size)
			world = Arrays.copyOf(world, size * 2);
		world[size++] = shape;
	}
	
	@Override
	protected void remove0(T shape) {
		final int index = Arrays.binarySearch(world, shape, comparator);
		world[index] = null;
		for(int i = index; i < size; i++)
			world[i] = world[i++];
	}
	
	@Override
	protected void reset0(T shape) {
	}
	
}
