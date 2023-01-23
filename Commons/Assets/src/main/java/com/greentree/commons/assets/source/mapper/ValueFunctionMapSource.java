package com.greentree.commons.assets.source.mapper;

import com.greentree.commons.assets.source.function.Source1Function;

public final class ValueFunctionMapSource<IN, OUT> extends AbstractMapSource<IN, OUT> {
	
	private static final long serialVersionUID = 1L;
	
	private final Source1Function<IN, OUT> function;
	
	public ValueFunctionMapSource(Mapper<OUT, OUT> result, Source1Function<IN, OUT> function) {
		super(result);
		this.function = function;
	}
	
	public ValueFunctionMapSource(Source1Function<IN, OUT> function) {
		this.function = function;
	}
	
	@Override
	protected OUT map(IN in) {
		return function.apply(in);
	}
	
	@Override
	protected OUT map(IN in, OUT oldValue) {
		return function.applyWithDest(in, oldValue);
	}
	
}
