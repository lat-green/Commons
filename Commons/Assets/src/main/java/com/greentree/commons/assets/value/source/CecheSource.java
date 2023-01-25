package com.greentree.commons.assets.value.source;


public final class CecheSource<T> implements Source<T> {
	
	private static final long serialVersionUID = 1L;
	
	private final Source<T> source;
	
	private transient T cache;
	
	private CecheSource(Source<T> source) {
		this.source = source;
		cache = source.get();
	}
	
	public static <T> Source<T> create(Source<T> source) {
		if(source.isConst())
			return source;
		return new CecheSource<>(source);
	}
	
	@Override
	public T get() {
		if(source.isChenge())
			cache = source.get();
		return cache;
	}
	
	@Override
	public boolean isChenge() {
		return source.isChenge();
	}
	
	@Override
	public boolean isConst() {
		return source.isConst();
	}
	
}
