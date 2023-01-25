package com.greentree.commons.assets.value.source;


public final class MutableSource<T> implements Source<T> {
	
	private static final long serialVersionUID = 1L;
	private boolean chenge;
	private T value;
	
	@Override
	public T get() {
		chenge = false;
		return value;
	}
	
	public void event() {
		chenge = true;
	}
	
	@Override
	public boolean isChenge() {
		return chenge;
	}
	
	@Override
	public boolean isConst() {
		return false;
	}
	
	public void set(T value) {
		chenge = true;
		this.value = value;
	}
	
}
