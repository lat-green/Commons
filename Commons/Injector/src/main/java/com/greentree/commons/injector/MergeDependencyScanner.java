package com.greentree.commons.injector;

import java.util.stream.Stream;


public record MergeDependencyScanner(DependencyScanner... scanners) implements DependencyScanner {
	
	@Override
	public Stream<? extends Dependency> scan(Class<?> cls) {
		return Stream.of(scanners).flatMap(x -> x.scan(cls));
	}
	
}
