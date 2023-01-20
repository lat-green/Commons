package com.greentree.commons.assets.value.factory;

import com.greentree.commons.assets.value.map.LazyValue;
import com.greentree.commons.assets.value.map.MapValue;


public class LazyValueFactory implements ValueFactory {
	
	@Override
	public <T, R> MapValue<T, R> create(MapValue<T, R> value) {
		return LazyValue.newValue(value);
	}
	
}
