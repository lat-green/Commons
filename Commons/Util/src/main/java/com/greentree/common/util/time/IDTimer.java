package com.greentree.common.util.time;

import java.util.HashMap;
import java.util.Map;

/** @author Arseny Latyshev */
public class IDTimer {

	private final Map<Integer, Long> timers;//id and start time


	public IDTimer() {
		timers = new HashMap<>();
	}

	private static long getTime() {
		return System.nanoTime();
	}

	public float finish(final int id) {
		final Long l = timers.remove(id);
		if(l == null) {
			System.err.println("Timer " + id + " alive Timers are not found");
			return 0;
		}
		return (getTime() - l) / 1000_000_000f;
	}

	public void start(final int id) {
		timers.put(id, getTime());
	}
}
