package com.greentree.commons.assets.value.map;

import com.greentree.commons.assets.store.Store;
import com.greentree.commons.assets.value.Value;

public interface MapValue<T, R> extends Value<R>, Store<T> {
	
	@Override
	default Value<R> toLazy() {
		return LazyValue.newValue(this);
	}
	
	default boolean isMutable() {
		return !isConst();
	}
	
	default Value<R> toNotMutable() {
		if(isMutable())
			return toConst();
		return this;
	}
	
}
