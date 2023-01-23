package com.greentree.commons.assets.value.map;

import com.greentree.commons.assets.value.function.Value1Function;

public final class ValueFuncMapValue<T, R> extends AbstractMapValue<T, R> {
	
	private static final long serialVersionUID = 1L;
	
	private final Value1Function<? super T, R> mapFunc;
	
	public ValueFuncMapValue(Value1Function<? super T, R> mapFunc) {
		this.mapFunc = mapFunc;
	}
	
	@Override
	protected R map(T value) {
		return mapFunc.apply(value);
	}
	
	@Override
	protected R map(T value, R dest) {
		return mapFunc.applyWithDest(value, dest);
	}
	
	@Override
	public String toString() {
		return "ValueFuncMapValue [" + mapFunc + "] " + super.toString();
	}
	
	
	
}
