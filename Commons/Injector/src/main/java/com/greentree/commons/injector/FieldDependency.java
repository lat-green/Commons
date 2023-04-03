package com.greentree.commons.injector;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

public record FieldDependency(Object host, Field field) implements Dependency {
	
	public FieldDependency {
		Objects.requireNonNull(host);
		Objects.requireNonNull(field);
		var mod = field.getModifiers();
		if(Modifier.isStatic(mod))
			throw new IllegalArgumentException("static field");
		if(Modifier.isFinal(mod))
			throw new IllegalArgumentException("final field");
	}
	
	@Override
	public String toString() {
		return "FieldDependency [" + field + "]";
	}
	
	@Override
	public void set(InjectionContainer container) {
		try {
			var value = value(container);
			var access = field.canAccess(host);
			try {
				field.setAccessible(true);
				field.set(host, value);
			}finally {
				field.setAccessible(access);
			}
		}catch(IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Object value(InjectionContainer container) {
		var value = container.get(field.getName(), field.getType());
		if(value != null)
			return value;
		return container.get(field.getType());
	}
	
}
