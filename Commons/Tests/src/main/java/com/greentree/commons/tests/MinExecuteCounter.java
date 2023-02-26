package com.greentree.commons.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;

public final class MinExecuteCounter implements Runnable, AutoCloseable {
	
	private final int minCount;
	private AtomicInteger count;
	
	public MinExecuteCounter(int maxCount) {
		this.minCount = maxCount;
		this.count = new AtomicInteger();
	}
	
	@Override
	public void run() {
		count.getAndIncrement();
	}
	
	@Override
	public void close() {
		final var v = count.get();
		if(minCount > v)
			fail("min run: " + minCount + ", but run: " + v);
	}
	
}
