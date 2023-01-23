package com.greentree.commons.assets.value.function;

import java.io.Serializable;
import java.util.function.Function;

public interface Value1Function<T, R> extends Function<T, R>, Serializable {
	
	@Override
	R apply(T value);
	
	default R applyWithDest(T value, R dest) {
		return apply(value);
	}
	
}
