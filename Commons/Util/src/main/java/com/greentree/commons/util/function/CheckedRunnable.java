package com.greentree.commons.util.function;

import com.greentree.commons.util.exception.WrappedException;

@FunctionalInterface
public interface CheckedRunnable extends Runnable {
	
	
	static CheckedRunnable build(Runnable runnable) {
		if(runnable instanceof CheckedRunnable r)
			return r;
		return ()->runnable.run();
	}
	
	@Override
	default void run() {
		try {
			checkedRun();
		}catch(Throwable e) {
			throw new WrappedException(e);
		}
	}
	
	void checkedRun() throws Throwable;
	
	@Deprecated
	default Runnable toNonCheked() {
		return this;
	}
}
