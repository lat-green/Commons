package com.greentree.commons.assets.value.map;

import com.greentree.commons.assets.value.Value;

public interface MapValue<T, R> extends Value<R> {
	
	void set(T value);
	
}
