package com.greentree.commons.property;

import java.util.function.Function;
import java.util.function.ToLongFunction;

public interface Property<T> extends BaseProperty<T, Property<T>> {
	
	default <R> Property<R> map(Function<? super T, ? extends R> mapFunction) {
		return MapProperty.create(this, mapFunction);
	}
	
	default LongProperty mapToLong(ToLongFunction<? super T> mapFunction) {
		return MapToLongProperty.create(this, mapFunction);
	}
	
	T get();
	
}
