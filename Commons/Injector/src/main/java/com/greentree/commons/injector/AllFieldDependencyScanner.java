package com.greentree.commons.injector;

import com.greentree.commons.reflection.ClassUtil;

import java.lang.reflect.Modifier;
import java.util.stream.Stream;

public class AllFieldDependencyScanner implements DependencyScanner {

    @Override
    public Stream<? extends Dependency> scan(Class<?> cls) {
        return ClassUtil.getAllFields(cls).stream().filter(x -> {
            var mod = x.getModifiers();
            if (Modifier.isStatic(mod))
                return false;
            return !Modifier.isFinal(mod);
        }).map(x -> {
            var type = x.getType();
            if (!type.isPrimitive())
                return new ObjectFieldDependency(x);
            if (type == int.class)
                return new IntFieldDependency(x);
            throw new UnsupportedOperationException("type: " + type);
        });
    }

}
