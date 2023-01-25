package com.greentree.commons.assets.source.provider;

import com.greentree.commons.assets.source.Source;


public final class ReduceProvider<T> implements SourceProvider<T> {
	
	public static final int CHARACTERISTICS = 0;
	private final SourceProvider<? extends Source<? extends T>> in;
	private SourceProvider<? extends T> source;
	
	public ReduceProvider(Source<? extends Source<? extends T>> in) {
		this.in = in.openProvider();
		this.source = this.in.get().openProvider();
	}
	
	public ReduceProvider(SourceProvider<? extends Source<? extends T>> in) {
		this.in = in;
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
