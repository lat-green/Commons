package com.greentree.commons.geometry.geom2d;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.greentree.commons.geometry.geom2d.IMovableShape2D;
import com.greentree.commons.geometry.geom2d.collision.strategy.dual.BruteDualCollisionStrategy;
import com.greentree.commons.geometry.geom2d.shape.Circle2D;
import com.greentree.commons.util.cortege.UnOrentetPair;

public class BruteDualCollisionStrategyTest {

	@Test
	void equalsShapeNotCollide() {
		BruteDualCollisionStrategy<WrapCollidable> s = new BruteDualCollisionStrategy<>();

		IMovableShape2D s1 = new Circle2D();
		final var w = new WrapCollidable(s1);

		assertTrue(s.getCollisionContact(Arrays.asList(w), Arrays.asList(w)).isEmpty());
	}

	@Test
	void simpleCollisionTest() {
		BruteDualCollisionStrategy<WrapCollidable> s = new BruteDualCollisionStrategy<>();

		IMovableShape2D s1 = new Circle2D();
		s1.add(10, 0);
		IMovableShape2D s2 = new Circle2D();
		IMovableShape2D s3 = new Circle2D();
		final var w1 = new WrapCollidable(s1);
		final var w2 = new WrapCollidable(s2);
		final var w3 = new WrapCollidable(s3);

		final var collection = Arrays.asList(w1, w2, w3);

		var res = s.getCollisionContact(collection, collection);

		assertEquals(1, res.size());

		assertEquals(res.iterator().next(), new UnOrentetPair<>(w2, w3));
	}


}
