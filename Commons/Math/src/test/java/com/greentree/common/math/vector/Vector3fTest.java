package com.greentree.common.math.vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.commons.math.vector.Vector2f;
import com.greentree.commons.math.vector.Vector3f;

public class Vector3fTest {

	@Test
	void getProjection() {
		var n = of(0, 1, 0);

		assertEquals(of(0,0,0).getProjection(n), of(0,0));
		assertEquals(of(0,1,0).getProjection(n), of(0,0));
		assertEquals(of(0,1,1).getProjection(n), of(0,1.5f,1).getProjection(n));
	}
	private Vector2f of(float x, float y) {
		return new Vector2f(x, y);
	}
	private Vector3f of(float x, float y, float z) {
		return new Vector3f(x, y, z);
	}

	@Test
	void testEquals1() {
		AbstractVector3f v1 = new Vector3f(1, 0, 0);
		AbstractVector3f v2 = new Vector3f(1, 0, 0);

		assertEquals(v1, v2);
	}

	@Test
	void testEquals2() {
		AbstractVector3f v1 = new Vector3f(1, 1, -0);
		AbstractVector3f v2 = new Vector3f(1, 1, 0);

		assertEquals(v1, v2);
	}

	@Test
	void testGetProjection() {
		AbstractVector3f v = new Vector3f(1, 0, 0);
		AbstractVector3f normal = new Vector3f(0, 1, 0);

		assertEquals(v.getProjection(normal), v.add(normal, new Vector3f()).getProjection(normal));

	}


	@Test
	void testNotEquals() {
		AbstractVector3f v1 = new Vector3f(1, 0, 0);
		AbstractVector3f v2 = new Vector3f(0, 1, 0);

		assertNotEquals(v1, v2);
	}

}
