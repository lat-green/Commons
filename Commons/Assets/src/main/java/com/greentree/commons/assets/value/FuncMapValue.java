package com.greentree.commons.assets.value;

import java.util.function.Function;

import com.greentree.commons.assets.value.map.MapValueImpl;

public final class FuncMapValue<T, R> extends MapValueImpl<T, R> {
	
	private static final long serialVersionUID = 1L;
	
	private final Function<? super T, ? extends R> mapFunc;
	
	FuncMapValue(Function<? super T, ? extends R> mapFunc) {
		super();
		this.mapFunc = mapFunc;
	}
	
	@Override
	protected R map(T value) {
		return mapFunc.apply(value);
	}
	
	@Override
	protected R map(T value, R dest) {
		return map(value);
	}
	
	@Override
	public String toString() {
		return "Func [" + mapFunc + "]" + super.toString();
	}
	
}
