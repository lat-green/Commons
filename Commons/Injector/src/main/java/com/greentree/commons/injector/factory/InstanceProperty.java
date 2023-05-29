package com.greentree.commons.injector.factory;

import java.lang.reflect.Field;

public interface InstanceProperty {
	
	Object value();
	
	default void set(Field field, Object object) throws IllegalAccessException {
		field.set(object, value());
	}
	
}
