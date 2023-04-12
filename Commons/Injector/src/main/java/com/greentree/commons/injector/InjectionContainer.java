package com.greentree.commons.injector;

import java.util.Optional;

public interface InjectionContainer {
	
	default Optional<Object> get(String name) {
		throw new UnsupportedOperationException();
	}
	
	default <T> Optional<T> get(String name, Class<T> cls) {
		return get(cls);
	}
	
	<T> Optional<T> get(Class<T> cls);
	
}
