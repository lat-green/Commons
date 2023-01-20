package com.greentree.common.math.vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.joml.Matrix2f;
import org.junit.jupiter.api.Test;

import com.greentree.common.math.Mathf;

public class MatrixTest {

	@Test
	void Matrix2frot2D() {
		Matrix2f r = Mathf.rot2(Mathf.PI * 0.5f);
		var vec = new Vector2f(1, 0);
		vec.mul(r);
		assertEquals(vec, new Vector2f(0, 1));
		vec.mul(r);
		assertEquals(vec, new Vector2f(-1, 0));
		vec.mul(r);
		assertEquals(vec, new Vector2f(0, -1));
		vec.mul(r);
		assertEquals(vec, new Vector2f(1, 0));
		
		
	}

}
