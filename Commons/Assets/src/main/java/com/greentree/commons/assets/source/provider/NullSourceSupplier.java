package com.greentree.commons.assets.source.provider;

public record NullSourceSupplier<T>() implements SourceProvider<T> {
	
	
	public static final int CHARACTERISTICS = CONST | BLANCK_CLOSE;
	
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
