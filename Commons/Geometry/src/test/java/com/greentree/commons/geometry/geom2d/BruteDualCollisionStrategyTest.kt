package com.greentree.commons.geometry.geom2d

import com.greentree.commons.geometry.geom2d.collision.strategy.dual.BruteDualCollisionStrategy
import com.greentree.commons.geometry.geom2d.shape.Circle2D
import com.greentree.commons.util.cortege.UnOrentetPair
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class BruteDualCollisionStrategyTest {

	@Test
	fun equalsShapeNotCollide() {
		val s = BruteDualCollisionStrategy<WrapCollidable>()
		val s1 = Circle2D()
		val w = WrapCollidable(s1)

		Assertions.assertTrue(s.getCollisionContact(listOf(w), Arrays.asList(w)).isEmpty())
	}

	@Test
	fun simpleCollisionTest() {
		val s = BruteDualCollisionStrategy<WrapCollidable>()
		val s1 = Circle2D(10f, 0f, 1f)
		val s2 = Circle2D()
		val s3 = Circle2D()
		val w1 = WrapCollidable(s1)
		val w2 = WrapCollidable(s2)
		val w3 = WrapCollidable(s3)
		val collection = Arrays.asList(w1, w2, w3)
		val res: Collection<UnOrentetPair<WrapCollidable>?> = s.getCollisionContact(collection, collection)

		Assertions.assertEquals(1, res.size)

		Assertions.assertEquals(res.iterator().next(), UnOrentetPair(w2, w3))
	}
}
