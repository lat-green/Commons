package com.greentree.commons.assets.value.source;

import java.util.function.Function;

public class LambdaMapSource<T, R> extends AbstractMapSource<T, R> {
	
	private static final long serialVersionUID = 1L;
	private final Function<T, R> function;
	
	public LambdaMapSource(Source<T> source, Function<T, R> function) {
		super(source);
		this.function = function;
	}
	
	@Override
	protected R map(T t) {
		return function.apply(t);
	}
	
	
	
}
