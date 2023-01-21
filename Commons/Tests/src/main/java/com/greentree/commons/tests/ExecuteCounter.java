package com.greentree.commons.tests;

import static org.junit.jupiter.api.Assertions.*;

public final class ExecuteCounter implements Runnable, AutoCloseable {
	
	private final int maxCount;
	private int count;
	
	public ExecuteCounter(int maxCount) {
		this.maxCount = maxCount;
		this.count = 0;
	}
	
	@Override
	public void run() {
		assertTrue(maxCount > count++, ()->"maxCount:" + maxCount);
	}
	
	@Override
	public void close() {
		assertEquals(maxCount, count);
	}
	
}
