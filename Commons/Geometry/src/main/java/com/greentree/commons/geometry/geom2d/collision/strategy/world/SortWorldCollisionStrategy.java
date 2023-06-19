package com.greentree.commons.geometry.geom2d.collision.strategy.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import com.greentree.commons.geometry.geom2d.AABB;
import com.greentree.commons.geometry.geom2d.Shape2DUtil;
import com.greentree.commons.geometry.geom2d.collision.Collidable2D;
import com.greentree.commons.util.cortege.Pair;


public class SortWorldCollisionStrategy<T extends Collidable2D> extends WorldCollisionStrategy<T> {
	
	private static final Comparator<Collidable2D> comparator = Comparator
			.comparing(c->c.getShape().getAABB().getMinX());
	private Collidable2D[] world = new Collidable2D[10];//10 : start size
	private int size = 0;
	private boolean reset;
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Pair<T, T>> getCollisionContact() {
		if(reset) {
			reset = true;
			Arrays.sort(world, 0, size, comparator);
		}
		final Collection<Pair<T, T>> res = new ArrayList<>();
		AABB[] aabbs = new AABB[world.length];
		for(int i = size - 1; i >= 0; i--) {
			final var a = world[i];
			final var sa = a.getShape();
			var aAABB = sa.getAABB();
			aabbs[i] = aAABB;
			for(int j = i + 1; j < size; j++) {
				final var b = world[j];
				var bAABB = aabbs[j];
				if(aAABB.getMaxX() < bAABB.getMinX())
					break;
				final var sb = b.getShape();
				if(Shape2DUtil.isIntersect(sa, sb))
					res.add(new Pair<>((T) a, (T) b));
			}
		}
		return res;
	}
	
	@Override
	protected void add0(T shape) {
		if(shape == null)
			throw new RuntimeException();
		if(world.length <= size)
			world = Arrays.copyOf(world, size * 2);
		world[size++] = shape;
		reset = true;
	}
	
	@Override
	protected void remove0(T shape) {
		final int index = Arrays.binarySearch(world, 0, size, shape, comparator);
		if(index < 0)
			return;
		for(int i = index; i < size - 1; i++)
			world[i] = world[i + 1];
		size--;
	}
	
	@Override
	protected void reset0(T shape) {
		reset = true;
	}
	
}
