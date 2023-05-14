package com.greentree.commons.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;

public final class MinExecuteCounter implements Runnable, AutoCloseable {
	
	private final int minCount;
	private AtomicInteger incrementCount;
	
	public MinExecuteCounter(int minCount) {
		this.minCount = minCount;
		this.incrementCount = new AtomicInteger();
	}
	
	@Override
	public void run() {
		incrementCount.getAndIncrement();
	}
	
	@Override
	public void close() {
		final var v = incrementCount.get();
		if(minCount > v)
			fail("min run: " + minCount + ", but run: " + v);
	}
	
}
