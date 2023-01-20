package com.greentree.commons.util.factory;

import java.lang.reflect.Field;

public final class IntProperty implements InstanceProperty {
	
	private final int value;
	
	public IntProperty(int value) {
		this.value = value;
	}
	
	@Override
	public void set(Field field, Object object) throws IllegalAccessException {
		field.setInt(object, value);
	}
	
	@Override
	public String toString() {
		return "IntProperty [" + value + "]";
	}
	
	@Override
	public Object value() {
		return value;
	}
	
}
