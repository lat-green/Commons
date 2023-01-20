package com.greentree.common.image;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.greentree.common.util.cortege.Pair;

public class ImageUtilTest {

	private static final float EPS = 1.0001f / 255;

	static Stream<Pair<Float, Byte>> tests() {
		return Stream.of(pair(1, -1), pair(0.5f, 127), pair(0.498f, 126), pair(0f, 0));
	}

	private static Pair<Float, Byte> pair(float f, int b) {
		return new Pair<>(f, (byte) b);
	}

	static Stream<Byte> bytes() {
		final var bs = new ArrayList<Byte>();
		for(int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
			bs.add((byte) i);
		}
		return bs.stream();
	}

	static Stream<Float> floats() {
		final var bs = new ArrayList<Float>();
		for(int i = 0; i <= (Byte.MAX_VALUE - Byte.MIN_VALUE); i++) {
			final var x = i / 255f;
			bs.add(x + EPS / 2);
			bs.add(x + EPS / 4);
			bs.add(x);
			bs.add(x - EPS / 4);
			bs.add(x - EPS / 2);
		}
		return bs.stream();
	}

	@MethodSource("bytes")
	@ParameterizedTest
	void byte_oubleConversion(byte i) {
		final var f = ImageUtil.to_float(i);
		final var e = ImageUtil.to_byte(f);
		assertEquals(e, i);
	}

	@MethodSource("floats")
	@ParameterizedTest
	void float_oubleConversion(float x) {
		final var e = ImageUtil.to_float(ImageUtil.to_byte(x));
		assertFloatEquals(e, x);
	}

	@MethodSource("tests")
	@ParameterizedTest
	void to_byte(Pair<Float, Byte> pair) {
		final float f = pair.first;
		final byte b = pair.seconde;

		assertEquals(ImageUtil.to_byte(f), b);
	}

	@MethodSource("tests")
	@ParameterizedTest
	void to_float(Pair<Float, Byte> pair) {
		final float f = pair.first;
		final byte b = pair.seconde;
		assertFloatEquals(ImageUtil.to_float(b), f);
	}

	private void assertFloatEquals(float expected, float actual) {
		assertFloatEquals(expected, actual, "");
	}

	private void assertFloatEquals(float expected, float actual, Object message) {
		assertTrue(Math.abs(expected - actual) < EPS, message.toString() + " " + expected + " != " + actual);
	}

}
