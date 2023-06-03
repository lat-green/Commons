package com.greentree.commons.reflection.finder;

import com.greentree.commons.reflection.ClassUtil;

import java.util.stream.Stream;

public interface ClassFinder {

    default Class<?> findClass(String name) throws ClassNotFoundException {
        return findClass(name, Object.class);
    }

    default <T> Class<? extends T> findClass(String name, Class<T> superClass) throws ClassNotFoundException {
        var opt = findClasses(name).filter(x -> ClassUtil.isExtends(superClass, x)).findAny();
        if (opt.isEmpty())
            throw new ClassNotFoundException(name + " " + superClass);
        return (Class<? extends T>) opt.get();
    }

    Stream<? extends Class<?>> findClasses(String name);

}
