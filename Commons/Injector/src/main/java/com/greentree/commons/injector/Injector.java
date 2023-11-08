package com.greentree.commons.injector;

import com.greentree.commons.util.UnsafeUtil;

public record Injector(InjectionContainer container, DependencyScanner scanner) {

    public Injector {
    }

    public <T> Constructor<T> newInstance(Class<T> cls) throws InstantiationException {
        var unsafe = UnsafeUtil.getUnsafeInstance();
        var instance = (T) unsafe.allocateInstance(cls);
        return new Constructor<>() {

            @Override
            public T value() {
                return instance;
            }

            @Override
            public void inject() {
                Injector.this.inject(instance);
            }
        };
    }

    public void inject(Object obj) {
        var deps = scanner.scan(obj.getClass());
        deps.forEach(x -> x.set(obj, container));
    }

}
