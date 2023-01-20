package com.greentree.common.util.function;

import java.lang.ref.SoftReference;
import java.util.function.Function;

public abstract class SoftSaveFunction<T, R> extends AbstractSaveFunction<T, SoftReference<R>>
		implements Function<T, R> {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected boolean valid(SoftReference<R> result) {
		return result.get() != null;
	}
	
	@Override
	public R apply(T t) {
		return applyRaw(t).get();
	}
	
	@Override
	protected SoftReference<R> create(T t) {
		return new SoftReference<>(createData(t));
	}
	
	protected abstract R createData(T t);
	
}
