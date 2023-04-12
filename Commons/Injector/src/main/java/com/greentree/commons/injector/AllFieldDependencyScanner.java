package com.greentree.commons.injector;

import java.lang.reflect.Modifier;
import java.util.stream.Stream;

import com.greentree.commons.util.classes.ClassUtil;

public class AllFieldDependencyScanner implements DependencyScanner {
	
	@Override
	public Stream<? extends Dependency> scan(Class<?> cls) {
		return ClassUtil.getAllFields(cls).stream().filter(x -> {
			var mod = x.getModifiers();
			if(Modifier.isStatic(mod))
				return false;
			if(Modifier.isFinal(mod))
				return false;
			return true;
		}).map(x -> new FieldDependency(x));
	}
	
}
