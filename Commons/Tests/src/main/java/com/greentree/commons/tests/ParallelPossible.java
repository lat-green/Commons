package com.greentree.commons.tests;

import java.util.concurrent.atomic.AtomicInteger;

public class ParallelPossible implements Runnable, AutoCloseable {
	
	private final AtomicInteger count;
	private final long deadLockTimeOut;
	
	public ParallelPossible(int threadCount, long deadLockTimeOut) {
		count = new AtomicInteger(threadCount);
		this.deadLockTimeOut = deadLockTimeOut;
	}
	
	public ParallelPossible(int threadCount) {
		this(threadCount, Sleeper.DEADLOCK_TIMEOUT);
	}
	
	@Override
	public void run() {
		final var s = new Sleeper(deadLockTimeOut);
		count.decrementAndGet();
		while(count.get() > 0)
			s.sleep();
	}
	
	@Override
	public void close() {
		if(count.get() > 0) {
			count.set(0);
			throw new RuntimeException("ParallelPossible not run");
		}
	}
	
}
