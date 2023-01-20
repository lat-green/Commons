package com.greentree.commons.geometry.geom2d.collision.strategy.dual;

import java.util.Collection;
import java.util.HashSet;

import com.greentree.commons.geometry.geom2d.collision.Collidable2D;
import com.greentree.commons.util.cortege.UnOrentetPair;

public class BruteDualCollisionStrategy<T extends Collidable2D>
		implements DualCollisionStrategy<T> {
	
	@Override
	public HashSet<UnOrentetPair<T>> getCollisionContact(Collection<T> ac, Collection<T> bc) {
		HashSet<UnOrentetPair<T>> res = new HashSet<>();
		int i = 0, j;
		for(var a : ac) {
			j = 0;
			for(var b : bc)
				if(i < j)
					break;
				else {
					if(a != b && a.getShape().isIntersect(b.getShape()))
						res.add(new UnOrentetPair<>(a, b));
					j++;
				}
			i++;
		}
		return res;
	}
	
}
