package com.greentree.commons.assets.source;

import com.greentree.commons.assets.source.function.Source1Function;
import com.greentree.commons.assets.source.provider.SourceFunctionMapProvider;
import com.greentree.commons.assets.source.provider.SourceProvider;

public final class SourceFunctionMapSource<T, R> implements Source<R> {
	
	private static final long serialVersionUID = 1L;
	private final Source<T> source;
	private final Source1Function<? super T, R> function;
	
	public SourceFunctionMapSource(Source<T> source, Source1Function<? super T, R> function) {
		this.source = source;
		this.function = function;
	}
	
	@Override
	public int characteristics() {
		return SourceFunctionMapProvider.CHARACTERISTICS;
	}
	
	@Override
	public SourceProvider<R> openProvider() {
		return new SourceFunctionMapProvider(source, function);
	}
	
}
