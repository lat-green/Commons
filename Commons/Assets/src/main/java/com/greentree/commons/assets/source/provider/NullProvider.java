package com.greentree.commons.assets.source.provider;

public record NullProvider<T>() implements SourceProvider<T> {
	
	
	public static final int CHARACTERISTICS = CONST | BLANCK_CLOSE | CHANGE_NO_EQUALS;
	
	@Override
	public void close() {
	}
	
	@Override
	public boolean isChenge() {
		return false;
	}
	
	@Override
	public T get() {
		return null;
	}
	
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
}
