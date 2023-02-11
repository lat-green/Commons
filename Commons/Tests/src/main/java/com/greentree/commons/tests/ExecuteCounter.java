package com.greentree.commons.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;

public final class ExecuteCounter implements Runnable, AutoCloseable {
	
	private final int maxCount;
	private AtomicInteger count;
	
	public ExecuteCounter(int maxCount) {
		this.maxCount = maxCount;
		this.count = new AtomicInteger();
	}
	
	@Override
	public void run() {
		assertTrue(maxCount > count.getAndIncrement(), ()->"maxCount:" + maxCount);
	}
	
	@Override
	public void close() {
		assertEquals(maxCount, count.get());
	}
	
}
