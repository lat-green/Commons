package com.greentree.commons.tests;



public final class Sleeper {
	
	public static final long DEADLOCK_TIMEOUT = 1000;
	private final long deadLockTimeOut;
	
	
	public Sleeper() {
		this(DEADLOCK_TIMEOUT);
	}
	
	public Sleeper(long deadLockTimeOut) {
		this.deadLockTimeOut = System.currentTimeMillis() + deadLockTimeOut;
	}
	
	public void sleep() {
		if(deadLockTimeOut < System.currentTimeMillis())
			throw new RuntimeException("Sleeper dead lock");
		Thread.yield();
	}
	
}
