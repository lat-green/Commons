package com.greentree.commons.assets.value.factory;

import com.greentree.commons.assets.value.map.MapValue;


public class DefaultValueFactory implements ValueFactory {

	@Override
	public <T, R> MapValue<T, R> create(MapValue<T, R> value) {
		return value;
	}

}
