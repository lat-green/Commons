package com.greentree.common.util.function;

import java.util.function.Function;

public abstract class SaveFunction<T, R> extends AbstractSaveFunction<T, R> implements Function<T, R> {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public R apply(T t) {
		return applyRaw(t);
	}
	
}
