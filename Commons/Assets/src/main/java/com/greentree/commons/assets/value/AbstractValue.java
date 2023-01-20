package com.greentree.commons.assets.value;

import java.util.Objects;

public abstract class AbstractValue<T> implements Value<T> {
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(obj == null)
			return false;
		if(obj instanceof Value<?> value) {
			return Objects.equals(get(), value.get());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(get());
	}
	
}
