package com.greentree.commons.reflection.finder;

import java.util.stream.Stream;

import com.greentree.commons.reflection.ClassUtil;

public interface ClassFinder {
	
	Stream<Class<?>> findClasses(String name);
	
	default Class<?> findClass(String name) throws ClassNotFoundException {
		return findClass(name, Object.class);
	}
	
	default Class<?> findClass(String name, Class<?> superClass) throws ClassNotFoundException {
		var opt = findClasses(name).filter(x -> ClassUtil.isExtends(superClass, x)).findAny();
		if(opt.isEmpty())
			throw new ClassNotFoundException(name + " " + superClass);
		return opt.get();
	}
	
}
