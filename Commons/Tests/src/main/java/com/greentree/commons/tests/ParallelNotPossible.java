package com.greentree.commons.tests;

import java.util.concurrent.TimeUnit;

public class ParallelNotPossible implements Runnable, AutoCloseable {
	
	private final long waitTime;
	private volatile boolean run;
	
	public ParallelNotPossible(long waitTime, TimeUnit unit) {
		this.waitTime = unit.toMillis(waitTime);
	}
	
	@Override
	public void run() {
		if(run)
			throw new RuntimeException("run parallel");
		run = true;
		Thread.yield();
		try {
			Thread.sleep(waitTime);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		Thread.yield();
		run = false;
	}
	
	@Override
	public void close() {
		
	}
	
}
