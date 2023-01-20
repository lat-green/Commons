package com.greentree.common.util.function;

import com.greentree.common.util.exception.WrappedException;

@FunctionalInterface
public interface CheckedRunnable {

	void run() throws Exception;

	default Runnable toNonCheked() {
		return () -> {
			try {
				run();
			}catch(Exception e) {
				throw new WrappedException(e);
			}
		};
	}
}
