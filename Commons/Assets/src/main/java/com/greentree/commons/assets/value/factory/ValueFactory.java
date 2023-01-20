package com.greentree.commons.assets.value.factory;

import java.util.Objects;

import com.greentree.commons.assets.value.MutableValue;
import com.greentree.commons.assets.value.map.MapValue;

@FunctionalInterface
public interface ValueFactory {
	
	default ValueFactory andThen(ValueFactory after) {
		Objects.requireNonNull(after);
		return new ValueFactory() {
			
			@Override
			public <T, R> MapValue<T, R> create(MapValue<T, R> value) {
				return after.create(create(value));
			}
		};
	}
	
	default ValueFactory compose(ValueFactory before) {
		Objects.requireNonNull(before);
		return before.andThen(this);
	}
	
	default <T> MapValue<T, T> create() {
		return create(new MutableValue<>());
	}
	
	<T, R> MapValue<T, R> create(MapValue<T, R> value);
	
}
