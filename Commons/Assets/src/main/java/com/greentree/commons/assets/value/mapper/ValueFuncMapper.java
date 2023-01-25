package com.greentree.commons.assets.value.mapper;

import com.greentree.commons.assets.value.function.Value1Function;
import com.greentree.commons.assets.value.store.Store;

public final class ValueFuncMapper<T, R> extends AbstractMapper<T, R> {
	
	private static final long serialVersionUID = 1L;
	
	private final Value1Function<? super T, R> mapFunc;
	
	public ValueFuncMapper(Store<T, R> store, Value1Function<? super T, R> mapFunc) {
		super(store);
		this.mapFunc = mapFunc;
	}
	
	@Override
	public String toString() {
		return "ValueFuncMapValue [" + mapFunc + "] " + super.toString();
	}
	
	@Override
	protected AbstractMapper<T, R> copy(Store<T, R> store) {
		return new ValueFuncMapper<>(store, mapFunc);
	}
	
	@Override
	protected R map(T value) {
		return mapFunc.apply(value);
	}
	
	@Override
	protected R map(T value, R dest) {
		return mapFunc.applyWithDest(value, dest);
	}
	
	
	
}
