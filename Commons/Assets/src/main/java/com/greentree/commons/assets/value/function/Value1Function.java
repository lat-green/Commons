package com.greentree.commons.assets.value.function;

import java.io.Serializable;
import java.util.function.Function;

public interface Value1Function<T, R> extends Function<T, R>, Serializable {
	
	@Override
	R apply(T input);
	
	default R applyWithDest(T input, R dest) {
		return apply(input);
	}
	
	default void clear(R value) {
		
	}
	
}
