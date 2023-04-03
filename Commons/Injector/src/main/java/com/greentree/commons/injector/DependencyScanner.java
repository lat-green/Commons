package com.greentree.commons.injector;

import java.util.stream.Stream;

public interface DependencyScanner {
	
	Stream<? extends Dependency> scan(Object object);
	
}
