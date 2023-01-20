package com.greentree.commons.util.time;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** @author Arseny Latyshev */
public class PointTimer implements Iterable<Float> {

	private int index = 0;
	private long last = 0;
	private final List<Float> time;

	public PointTimer() {
		time = new ArrayList<>();
	}

	private static long getTime() {
		return System.nanoTime();
	}

	public float get(int i) {
		return time.get(i);
	}

	@Override
	public Iterator<Float> iterator() {
		return time.iterator();
	}

	public void point() {
		if(last == 0) last = getTime();
		else {
			long t = getTime();
			float delta = (t - last) / 1000_000_000f;
			if(index < time.size()) {
				Float l = time.get(index);
				time.set(index, l + delta);
			}else
				time.add(index, delta);
			last = t;
			index++;
		}
	}

	public void restart() {
		index = 0;
		last = 0;
	}

	@Override
	public String toString() {
		return "SmartTimer " + time;
	}
}
