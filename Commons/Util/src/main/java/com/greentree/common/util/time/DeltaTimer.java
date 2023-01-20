package com.greentree.common.util.time;

/** @author Arseny Latyshev */
public class DeltaTimer {

	private float delta, last;

	public float getDelta() {
		return delta;
	}

	public void step() {
		final float t = getTime();
		delta = t - last;
		last = t;
	}

	private float getTime() {
		return System.nanoTime() / 1_000_000_000f;
	}
}
