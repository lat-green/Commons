package com.greentree.commons.assets.value.store;

import java.util.function.Function;


public final class StoreImpl<T, R> implements Store<T, R> {
	
	private static final long serialVersionUID = 1L;
	
	private T source;
	private transient R result;
	
	@Override
	public void close() {
	}
	
	@Override
	public Store<T, R> copy() {
		final var copy = new StoreImpl<T, R>();
		copy.result = result;
		copy.source = source;
		return copy;
	}
	
	@Override
	public void init(Function<T, R> function) {
		result = function.apply(source);
	}
	
	@Override
	public R result() {
		return result;
	}
	
	@Override
	public void result(R result) {
		this.result = result;
	}
	
	@Override
	public void source(T source) {
		this.source = source;
	}
	
}
