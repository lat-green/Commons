package com.greentree.commons.util.function;

import java.util.function.Supplier;

import com.greentree.commons.util.exception.WrappedException;

@FunctionalInterface
public interface CheckedSupplier<T> extends Supplier<T> {
	
	
	@SuppressWarnings("unchecked")
	static <T> CheckedSupplier<T> build(Supplier<? extends T> supplier) {
		if(supplier instanceof CheckedSupplier)
			return (CheckedSupplier<T>) supplier;
		return ()->supplier.get();
	}
	
	T checkedGet() throws Exception;
	
	@Override
	default T get() {
		try {
			return checkedGet();
		}catch(Exception e) {
			throw new WrappedException(e);
		}
	}
	
	@Deprecated
	default Supplier<T> toNonCheked() {
		return this;
	}
	
}
