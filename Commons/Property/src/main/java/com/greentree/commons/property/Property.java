package com.greentree.commons.property;

import java.util.function.Function;

public interface Property<T> extends BaseProperty<T, Property<T>> {
	
	default <R> Property<R> map(Function<? super T, ? extends R> mapFunction) {
		return MapProperty.create(this, mapFunction);
	}
	
	T get();
	
}
