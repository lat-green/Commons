package com.greentree.common.math.vector;

import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.commons.math.vector.Vector2f;
import com.greentree.commons.math.vector.Vector3f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector3fTest {

	//	@Test
//	void getProjection() {
//		var n = of(0, 1, 0);
//
//		assertEquals(of(0,0,0).getProjection(n), of(0,0));
//		assertEquals(of(0,1,0).getProjection(n), of(0,0));
//		assertEquals(of(0,1,1).getProjection(n), of(0,1.5f,1).getProjection(n));
//	}
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

		assertVec3fEquals(v1, v2);
	}

	private void assertVec3fEquals(AbstractVector3f a, AbstractVector3f b) {
		assertEquals(a.getX(), b.getX(), Mathf.EPS);
		assertEquals(a.getY(), b.getY(), Mathf.EPS);
		assertEquals(a.getZ(), b.getZ(), Mathf.EPS);
	}

	@Test
	void testEquals2() {
		AbstractVector3f v1 = new Vector3f(1, 1, -0);
		AbstractVector3f v2 = new Vector3f(1, 1, 0);

		assertVec3fEquals(v1, v2);
	}

//	@Test
//	void testGetProjection() {
//		AbstractVector3f v = new Vector3f(1, 0, 0);
//		AbstractVector3f normal = new Vector3f(0, 1, 0);
//
//		assertEquals(v.getProjection(normal), v.add(normal, new Vector3f()).getProjection(normal));
//
//	}


}
