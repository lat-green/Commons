package com.greentree.commons.geometry.geom2d

import com.greentree.commons.geometry.geom2d.shape.Circle2D
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * @author Arseny Latyshev
 */
@DisplayName("AABB 2D isIntersect Tests")
class AABBisIntersectTest {

	@DisplayName("Circle as AABB isIntersect")
	@Test
	fun Circle() {
		val shape1 = Circle2D(0f, 0f, 1f)
		val shape2 = Circle2D(0f, 0f, 1f)
		val shape3 = Circle2D(3f, 0f, 1f)
		val shape4 = Circle2D(0f, 3f, 1f)
		val shape5 = Circle2D(2f, 2f, 1.5f)
		assertTrue(shape1.boundingBox.isIntersect(shape2.boundingBox))
		assertFalse(shape1.boundingBox.isIntersect(shape3.boundingBox))
		assertFalse(shape1.boundingBox.isIntersect(shape4.boundingBox))
		assertTrue(shape1.boundingBox.isIntersect(shape5.boundingBox))
		assertFalse(shape2.boundingBox.isIntersect(shape3.boundingBox))
		assertFalse(shape2.boundingBox.isIntersect(shape4.boundingBox))
		assertTrue(shape2.boundingBox.isIntersect(shape5.boundingBox))
		assertFalse(shape3.boundingBox.isIntersect(shape4.boundingBox))
		assertTrue(shape3.boundingBox.isIntersect(shape5.boundingBox))
		assertTrue(shape4.boundingBox.isIntersect(shape5.boundingBox))
	}
}
