package com.greentree.commons.assets.source;

import com.greentree.commons.assets.source.provider.ConstProvider;
import com.greentree.commons.assets.source.provider.SourceProvider;

public final class ConstSource<T> implements Source<T> {
	
	private static final long serialVersionUID = 1L;
	
	private final T value;
	
	private ConstSource(T value) {
		this.value = value;
	}
	
	public static <T> Source<T> newValue(T value) {
		if(value == null)
			return NullSource.instance();
		return new ConstSource<>(value);
	}
	
	@Override
	public String toString() {
		return "Const [" + value + "]";
	}
	
	@Override
	public SourceProvider<T> openProvider() {
		return new ConstProvider<>(value);
	}
	
	@Override
	public int characteristics() {
		return ConstProvider.CHARACTERISTICS;
	}
	
}
