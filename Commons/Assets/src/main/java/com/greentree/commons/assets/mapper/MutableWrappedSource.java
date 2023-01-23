package com.greentree.commons.assets.mapper;

import java.util.Objects;

import com.greentree.commons.assets.source.MutableSource;
import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.provider.SourceProvider;

public final class MutableWrappedSource<T> implements Source<T> {
	
	private static final long serialVersionUID = 1L;
	
	private final MutableSource<Source<T>> result = new MutableSource<>();
	
	public MutableWrappedSource(Source<T> source) {
		set(source);
	}
	
	public void set(Source<T> source) {
		Objects.requireNonNull(source);
		result.set(source);
	}
	
	@Override
	public int characteristics() {
		return MutableWrappedSourceSourceProvider.CHARACTERISTICS;
	}
	
	@Override
	public SourceProvider<T> openProvider() {
		return new MutableWrappedSourceSourceProvider<>(result);
	}
	
}
