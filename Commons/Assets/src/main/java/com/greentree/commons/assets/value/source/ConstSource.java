package com.greentree.commons.assets.value.source;


public final class ConstSource<T> implements Source<T> {
	
	private static final long serialVersionUID = 1L;
	private final T value;
	
	public ConstSource(T value) {
		this.value = value;
	}
	
	@Override
	public T get() {
		return value;
	}
	
	@Override
	public boolean isChenge() {
		return false;
	}
	
	@Override
	public boolean isConst() {
		return true;
	}
	
}
