package com.greentree.commons.coroutine;

public final class Sleep implements YieldExpretion {
	
	private final long endTime;
	
	public Sleep(long t) {
		this.endTime = System.currentTimeMillis() + t;
	}
	
	@Override
	public boolean isDone() {
		return System.currentTimeMillis() > endTime;
	}
	
}
