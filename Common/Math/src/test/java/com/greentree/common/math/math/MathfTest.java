package com.greentree.common.math.math;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.greentree.common.math.Mathf;



public class MathfTest {

	private static final double EPS = 1E-3f;

	static Stream<Float> angles() {
		final int STEPS = 1000;
		final float MIN = -8 * Mathf.PI;
		final float MAX = 8 * Mathf.PI;
		final float step = (MAX - MIN) / STEPS;

		return Stream.iterate(MIN, a -> a < MAX, a -> a + step);
	}

	static Stream<Float> anglesDefault() {
		return Stream.of(Mathf.PI, Mathf.PI2, Mathf.PIHalf);
	}

	@ParameterizedTest()
	@MethodSource(value = {"angles", "anglesDefault"})
	void cos(float a) {
		var sin = Mathf.sin(a);
		var cos1 = Mathf.cos(a);
		var cos2 = Mathf.cos(a, sin);
		var abs = Mathf.abs(cos1 - cos2);
		assertTrue(abs < EPS, cos1 + " " + cos2 + " " + abs);
	}

	@ParameterizedTest()
	@MethodSource(value = {"angles", "anglesDefault"})
	void sin(float a) {
		var cos = Mathf.cos(a);
		var sin1 = Mathf.sin(a);
		var sin2 = Mathf.sin(a, cos);
		var abs = Mathf.abs(sin1 - sin2);
		assertTrue(abs < EPS, sin1 + " " + sin2 + " " + abs);
	}
}
