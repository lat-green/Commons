package com.greentree.commons.assets.value;

import com.greentree.commons.assets.value.function.Value1Function;
import com.greentree.commons.assets.value.map.MapValueImpl;

public final class ValueFuncMapValue<T, R> extends MapValueImpl<T, R> {
	
	private static final long serialVersionUID = 1L;
	
	private final Value1Function<? super T, R> mapFunc;
	
	ValueFuncMapValue(Value1Function<? super T, R> mapFunc) {
		this.mapFunc = mapFunc;
	}
	
	@Override
	public boolean isNull() {
		return super.isNull() || mapFunc.isNull(source());
	}
	
	@Override
	public String toString() {
		return "ValueFunc [" + mapFunc.getClass().getSimpleName() + "] " + super.toString();
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
