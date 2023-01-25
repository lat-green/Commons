package com.greentree.commons.assets.source.provider;

public record ConstProvider<T>(T value) implements SourceProvider<T> {
	
	public static final int CHARACTERISTICS = CONST | NOT_NULL | BLANCK_CLOSE | CHANGE_NO_EQUALS;
	
	@Override
	public void close() {
	}
	
	@Override
	public boolean isChenge() {
		return false;
	}
	
	@Override
	public T getOld() {
		return value;
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public T get() {
		return value;
	}
	
}
