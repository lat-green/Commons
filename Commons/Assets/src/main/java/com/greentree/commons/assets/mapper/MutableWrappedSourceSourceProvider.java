package com.greentree.commons.assets.mapper;

import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.provider.SourceProvider;


public final class MutableWrappedSourceSourceProvider<T> implements SourceProvider<T> {
	
	public static final int CHARACTERISTICS = 0;
	private final SourceProvider<? extends Source<? extends T>> in;
	private SourceProvider<? extends T> source;
	
	public MutableWrappedSourceSourceProvider(Source<? extends Source<? extends T>> in) {
		this.in = in.openProvider();
		this.source = this.in.get().openProvider();
	}
	
	@Override
	public int characteristics() {
		return source.characteristics();
	}
	
	@Override
	public boolean isChenge() {
		return source.isChenge() || in.isChenge();
	}
	
	@Override
	public void close() {
		source.close();
		in.close();
	}
	
	@Override
	public T get() {
		if(in.isChenge()) {
			source.close();
			source = this.in.get().openProvider();
		}
		return source.get();
	}
	
}
