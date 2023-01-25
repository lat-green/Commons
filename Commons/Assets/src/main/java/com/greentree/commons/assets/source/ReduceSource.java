package com.greentree.commons.assets.source;

import java.util.Objects;

import com.greentree.commons.assets.source.provider.ReduceProvider;
import com.greentree.commons.assets.source.provider.SourceProvider;

public final class ReduceSource<T> implements Source<T> {
	
	private static final long serialVersionUID = 1L;
	
	private final MutableSource<Source<T>> result = new MutableSource<>();
	
	public ReduceSource(Source<T> source) {
		set(source);
	}
	
	public void set(Source<T> source) {
		Objects.requireNonNull(source);
		result.set(source);
	}
	
	@Override
	public int characteristics() {
		return ReduceProvider.CHARACTERISTICS;
	}
	
	@Override
	public SourceProvider<T> openProvider() {
		return new ReduceProvider<>(result);
	}
	
}
