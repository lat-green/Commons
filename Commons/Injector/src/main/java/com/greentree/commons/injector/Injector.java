package com.greentree.commons.injector;

import java.lang.reflect.InvocationTargetException;

import com.greentree.commons.util.classes.ClassUtil;

public record Injector(InjectionContainer container, DependencyScanner scanner) {
	
	public Injector {
		
	}
	
	public void inject(Object obj) {
		var deps = scanner.scan(obj.getClass());
		deps.forEach(x -> x.set(obj, container));
	}
	
	public <T> Constructor<T> newInstace(Class<T> cls) throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		var inst = ClassUtil.newInstance(cls);
		
		return new Constructor<>() {
			
			@Override
			public T value() {
				return inst;
			}
			
			@Override
			public void inject() {
				Injector.this.inject(inst);
			}
		};
	}
	
}
