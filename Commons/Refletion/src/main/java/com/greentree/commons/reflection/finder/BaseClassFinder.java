package com.greentree.commons.reflection.finder;

import java.util.stream.Stream;

public record BaseClassFinder<T>(ClassFinder origin, Class<T> baseClass) implements ClassFinder {

    @Override
    public Stream<? extends Class<? extends T>> findClasses(String name) {
        return null;
    }

}
