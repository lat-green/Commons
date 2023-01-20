package com.greentree.common.util.function;

import java.util.function.Supplier;

import com.greentree.common.util.exception.WrappedException;

@FunctionalInterface
public interface CheckedSupplier<T> {

	T get() throws Exception;

	default Supplier<T> toNonCheked() {
		return () -> {
			try {
				return get();
			}catch(Exception e) {
				throw new WrappedException(e);
			}
		};
	}

}
