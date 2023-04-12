package com.greentree.commons.injector;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.Optional;

public record FieldDependency(Field field) implements Dependency {
	
	public FieldDependency {
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
	public void set(Object host, InjectionContainer container) {
		try {
			var optional = valueOptional(container);
			if(optional.isEmpty())
				throw new IllegalArgumentException("not found value to " + field + " to host:" + host);
			var access = field.canAccess(host);
			try {
				field.setAccessible(true);
				field.set(host, optional.get());
			}finally {
				field.setAccessible(access);
			}
		}catch(IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Optional<?> valueOptional(InjectionContainer container) {
		var value = container.get(field.getName(), field.getType());
		if(value.isPresent())
			return value;
		return container.get(field.getType());
	}
	
}
