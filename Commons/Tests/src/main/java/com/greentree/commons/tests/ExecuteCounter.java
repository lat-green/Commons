package com.greentree.commons.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;

public final class ExecuteCounter implements Runnable, AutoCloseable {
	
	private final int count;
	private AtomicInteger incrementCount;
	
	public ExecuteCounter(int count) {
		this.count = count;
		this.incrementCount = new AtomicInteger();
	}
	
	@Override
	public void run() {
		assertTrue(count > incrementCount.getAndIncrement(), () -> "count:" + count);
	}
	
	@Override
	public void close() {
		assertEquals(count, incrementCount.get());
	}
	
}
