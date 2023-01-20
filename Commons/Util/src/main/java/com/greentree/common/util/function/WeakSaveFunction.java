package com.greentree.common.util.function;

import java.lang.ref.WeakReference;
import java.util.function.Function;

public abstract class WeakSaveFunction<T, R> extends AbstractSaveFunction<T, WeakReference<R>>
		implements Function<T, R> {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected boolean valid(WeakReference<R> result) {
		return result.get() != null;
	}
	
	@Override
	public R apply(T t) {
		return applyRaw(t).get();
	}
	
	@Override
	protected WeakReference<R> create(T t) {
		return new WeakReference<>(createData(t));
	}
	
	protected abstract R createData(T t);
	
}
