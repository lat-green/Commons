package com.greentree.commons.assets.value.source;


public abstract class AbstractMapSource<T, R> implements Source<R> {
	
	private static final long serialVersionUID = 1L;
	
	private final Source<T> source;
	
	public AbstractMapSource(Source<T> source) {
		this.source = source;
	}
	
	@Override
	public final R get() {
		return map(source.get());
	}
	
	protected abstract R map(T t);
	
	@Override
	public final boolean isChenge() {
		return source.isChenge();
	}
	
	@Override
	public final boolean isConst() {
		return source.isConst();
	}
	
}
