package com.greentree.commons.reflection.finder;

import java.util.stream.Stream;

public record ClassLoaderClassFinder(ClassLoader classLoader) implements ClassFinder {
	
	@Override
	public Stream<Class<?>> findClasses(String name) {
		try {
			return Stream.of(classLoader.loadClass(name));
		}catch(Exception e) {
		}
		return Stream.empty();
	}
	
}
