package com.greentree.commons.assets.source;

import com.greentree.commons.assets.source.provider.CecheProvider;
import com.greentree.commons.assets.source.provider.SourceProvider;

public final class CecheSource<T> implements Source<T> {
	
	private static final long serialVersionUID = 1L;
	
	private final Source<T> source;
	
	private CecheSource(Source<T> source) {
		this.source = source;
	}
	
	public static <T> Source<T> ceche(Source<T> source) {
		if(source.hasConst())
			return source;
		return new CecheSource<>(source);
	}
	
	@Override
	public int characteristics() {
		return source.characteristics();
	}
	
	@Override
	public SourceProvider<T> openProvider() {
		return CecheProvider.ceche(source);
	}
	
}
