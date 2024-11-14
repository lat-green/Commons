package com.greentree.commons.geometry.geom2d.collision.strategy.dual

import com.greentree.commons.geometry.geom2d.collision.Collidable2D
import com.greentree.commons.geometry.geom2d.isIntersect
import com.greentree.commons.util.cortege.UnOrentetPair

class BruteDualCollisionStrategy<T : Collidable2D>
	: DualCollisionStrategy<T> {

	override fun getCollisionContact(ac: Collection<T>, bc: Collection<T>): HashSet<UnOrentetPair<T>> {
		val res = HashSet<UnOrentetPair<T>>()
		var i = 0
		var j: Int
		for(a in ac) {
			j = 0
			for(b in bc) if(i < j) break
			else {
				if(a !== b && a.shape.isIntersect(b.shape)) res.add(UnOrentetPair(a, b))
				j++
			}
			i++
		}
		return res
	}
}
