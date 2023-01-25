package com.greentree.commons.assets.source.merge;

import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.provider.SourceProvider;

public final class MISource<T> implements Source<Iterable<T>> {
	
	private static final long serialVersionUID = 1L;
	
	private final Iterable<? extends Source<? extends T>> sources;
	
	public MISource(Iterable<? extends Source<? extends T>> sources) {
		this.sources = sources;
	}
	
	@Override
	public int characteristics() {
		return MIProvider.CHARACTERISTICS;
	}
	
	@Override
	public SourceProvider<Iterable<T>> openProvider() {
		return new MIProvider<>(sources);
	}
	
	
}
