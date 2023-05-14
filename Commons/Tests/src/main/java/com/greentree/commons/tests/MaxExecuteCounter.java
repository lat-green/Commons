package com.greentree.commons.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;

public final class MaxExecuteCounter implements Runnable, AutoCloseable {
	
	private final int maxCount;
	private AtomicInteger incrementCount;
	
	public MaxExecuteCounter(int maxCount) {
		this.maxCount = maxCount;
		this.incrementCount = new AtomicInteger();
	}
	
	@Override
	public void run() {
		incrementCount.getAndIncrement();
	}
	
	@Override
	public void close() {
		final var v = incrementCount.get();
		if(maxCount < v)
			fail("max run: " + maxCount + ", but run: " + v);
	}
	
}
