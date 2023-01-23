package com.greentree.commons.assets.mapper;

import com.greentree.commons.assets.value.function.Value1Function;

public final class ValueFunctionMapSource<IN, OUT> extends AbstractMapSource<IN, OUT> {
	
	private static final long serialVersionUID = 1L;
	
	private final Value1Function<IN, OUT> function;
	
	public ValueFunctionMapSource(Mapper<OUT, OUT> result, Value1Function<IN, OUT> function) {
		super(result);
		this.function = function;
	}
	
	public ValueFunctionMapSource(Value1Function<IN, OUT> function) {
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
