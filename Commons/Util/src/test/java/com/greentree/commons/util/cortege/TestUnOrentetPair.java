package com.greentree.commons.util.cortege;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Arseny Latyshev
 *
 */
public class TestUnOrentetPair {

	public static void equals0(Object a, Object b) {
		assertEquals(a, b);
		assertEquals(b, a);
	}

	@Test
	public void equalsTest() {
		UnOrentetPair<Integer> a = new UnOrentetPair<>(4, 5);
		UnOrentetPair<Integer> b = new UnOrentetPair<>(5, 4);
		UnOrentetPair<Integer> c = new UnOrentetPair<>(7, 6);
		UnOrentetPair<Integer> d = new UnOrentetPair<>(7, 6);

		equals0(a, b);
		equals0(a, a);

		equals0(c, d);

		assertNotEquals(a, c);
		assertNotEquals(a, d);
		assertNotEquals(b, c);
	}


}
