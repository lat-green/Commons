package com.greentree.commons.assets.source.provider;

public record ConstSourceSupplier<T>(T value) implements SourceProvider<T> {
	
	public static final int CHARACTERISTICS = CONST | NOT_NULL | BLANCK_CLOSE;
	
	@Override
	public void close() {
	}
	
	@Override
	public boolean isChenge() {
		return false;
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
