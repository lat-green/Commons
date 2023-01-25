package com.greentree.commons.assets.source;

import java.io.ObjectStreamException;

import com.greentree.commons.assets.source.provider.NullProvider;
import com.greentree.commons.assets.source.provider.SourceProvider;

public final class NullSource<T> implements Source<T> {
	
	private static final long serialVersionUID = 1L;
	
	private static final NullSource<?> INSTANCE = new NullSource<>();
	
	private Object readResolve() throws ObjectStreamException {
		return INSTANCE;
	}
	
	private NullSource() {
	}
	
	@SuppressWarnings("unchecked")
	public static <T> NullSource<T> instance() {
		return (NullSource<T>) INSTANCE;
	}
	
	@Override
	public String toString() {
		return "NULL";
	}
	
	@Override
	public SourceProvider<T> openProvider() {
		return new NullProvider<>();
	}
	
	@Override
	public int characteristics() {
		return NullProvider.CHARACTERISTICS;
	}
	
}
