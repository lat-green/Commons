package com.greentree.commons.reflection.finder;

import java.util.stream.Stream;

public record ClassLoaderClassFinder(ClassLoader classLoader) implements ClassFinder {

    public ClassLoaderClassFinder() {
        this(ClassLoaderClassFinder.class.getClassLoader());
    }

    @Override
    public Stream<Class<?>> findClasses(String name) {
        try {
            return Stream.of(classLoader.loadClass(name));
        } catch (Exception ignore) {
        }
        return Stream.empty();
    }

}
