package com.greentree.commons.util.exception;

public final class SoftException {
	
	public static <T extends Throwable> void run(T throwable, Runnable runnable) throws T {
		try {
			runnable.run();
		}catch(Exception s) {
			throwable.addSuppressed(s);
		}
		throw throwable;
	}
	
}
