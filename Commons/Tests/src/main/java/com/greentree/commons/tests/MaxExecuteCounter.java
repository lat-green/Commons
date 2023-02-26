package com.greentree.commons.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;

public final class MaxExecuteCounter implements Runnable, AutoCloseable {
	
	private final int maxCount;
	private AtomicInteger count;
	
	public MaxExecuteCounter(int maxCount) {
		this.maxCount = maxCount;
		this.count = new AtomicInteger();
	}
	
	@Override
	public void run() {
		count.getAndIncrement();
	}
	
	@Override
	public void close() {
		final var v = count.get();
		if(maxCount < v)
			fail("max run: " + maxCount + ", but run: " + v);
	}
	
}
