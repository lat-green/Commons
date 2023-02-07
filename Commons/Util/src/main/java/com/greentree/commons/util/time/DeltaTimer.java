package com.greentree.commons.util.time;

/** @author Arseny Latyshev */
public class DeltaTimer {
	
	private long delta, last;
	
	public float getDelta() {
		return delta / 1_000_000_000f;
	}
	
	public void step() {
		final var t = getTime();
		delta = t - last;
		last = t;
	}
	
	private long getTime() {
		return System.nanoTime();
	}
}
