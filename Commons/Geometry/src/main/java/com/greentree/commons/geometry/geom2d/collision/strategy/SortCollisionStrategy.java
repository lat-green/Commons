package com.greentree.commons.geometry.geom2d.collision.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import com.greentree.commons.geometry.geom2d.AABB;
import com.greentree.commons.geometry.geom2d.Shape2DUtil;
import com.greentree.commons.geometry.geom2d.collision.Collidable2D;
import com.greentree.commons.util.cortege.Pair;


public class SortCollisionStrategy extends AbstractCollisionStrategy {
	
	private static final Comparator<Collidable2D> comparator = Comparator
			.comparing(c->c.getShape().getAABB().getMinX());
	
	//	private final static Timer t = new Timer();
	
	public SortCollisionStrategy() {
	}
	
	@Override
	public <T extends Collidable2D> Collection<Pair<T, T>> getCollisionContact(T[] world) {
		//		t.start(0);
		final Collection<Pair<T, T>> res = new ArrayList<>();
		//		final Collection<Pair<T, T>> res = new ArrayList<>();
		//		t.start(1);
		Arrays.sort(world, comparator);
		//		var t1 = t.finish(1);
		//		t.start(2);
		var aabbs = new AABB[world.length];
		for(var i = world.length - 1; i >= 0; i--) {
			var a = world[i];
			var aAABB = a.getShape().getAABB();
			aabbs[i] = aAABB;
			for(var j = i + 1; j < world.length; j++) {
				//			class A {
				//				static boolean flag = true;
				//			}
				//			A.flag = true;
				//			Stream.iterate(i+1, j -> j < world.length && A.flag, j -> j+1).parallel().forEach(j -> {
				var b = world[j];
				var bAABB = aabbs[j];
				if(aAABB.getMaxX() < bAABB.getMinX()) //					A.flag =
					break;
				if(Shape2DUtil.isIntersect(a.getShape(), b.getShape()))
					res.add(new Pair<>(a, b));
				//			});
			}
		}
		//		var t2 = t.finish(2);
		//		t.start(3);
		//		var t3 = t.finish(3);
		//		var f = t.finish(0);
		//		System.out.println(res.size() + " " + a.size());
		//		System.out.printf("%-10f%-10f%-10f%-10f%-10f\n", f, t1, t2, t3, f-(t1+t2+t3));
		return res;
		//		return res;
	}
	
}
