package com.greentree.commons.property;

import java.util.function.LongFunction;
import java.util.function.LongUnaryOperator;

public interface LongProperty extends BaseProperty<Long, LongProperty> {
	
	default <R> Property<R> mapToObj(LongFunction<? extends R> mapFunction) {
		return MapToObjProperty.create(this, mapFunction);
	}
	
	default LongProperty map(LongUnaryOperator mapFunction) {
		return MapLongProperty.create(this, mapFunction);
	}
	default Property<Long> toObj() {
		return mapToObj(x -> x);
	}
	
	long get();
}
