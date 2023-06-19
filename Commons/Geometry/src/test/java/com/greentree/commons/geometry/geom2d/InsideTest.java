package com.greentree.commons.geometry.geom2d;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.greentree.commons.geometry.geom2d.shape.Circle2D;
import com.greentree.commons.math.vector.Vector2f;

/** @author Arseny Latyshev */
@DisplayName("InsideTest 2D isInside Tests")
public class InsideTest {
	
	@Test
	public void Circle() {
		final var shape1 = new Circle2D(0, 0, 1);
		final var shape2 = new Circle2D(0, 1, 1);
		final var shape3 = new Circle2D(3, 0, 1);
		final var shape4 = new Circle2D(0, 3, 1);
		final var shape5 = new Circle2D(2, 2, 1.5f);
		
		final var p = new Vector2f(0, 0);
		
		assertTrue(shape1.isInside(p));
		assertTrue(shape2.isInside(p));
		assertFalse(shape3.isInside(p));
		assertFalse(shape4.isInside(p));
		assertFalse(shape5.isInside(p));
	}
	
}
