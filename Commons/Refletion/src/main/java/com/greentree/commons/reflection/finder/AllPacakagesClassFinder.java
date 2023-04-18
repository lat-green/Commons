package com.greentree.commons.reflection.finder;

import java.util.stream.Stream;

import com.greentree.commons.reflection.ClassUtil;

public record AllPacakagesClassFinder(ClassFinder base) implements ClassFinder {
	
	
	@Override
	public Stream<Class<?>> findClasses(String name) {
		return Stream.concat(base.findClasses(name),
				ClassUtil.getAllPackages().flatMap(x -> base.findClasses(x + "." + name)));
	}
	
	
}
