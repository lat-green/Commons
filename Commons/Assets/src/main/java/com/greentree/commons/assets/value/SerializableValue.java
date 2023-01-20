package com.greentree.commons.assets.value;

import java.io.Serializable;

public interface SerializableValue<T> extends Value<T>, Serializable {
	
	@Override
	default Serializable toSerializable() {
		return this;
	}
	
}
