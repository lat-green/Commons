package com.greentree.commons.injector;

import java.lang.reflect.InvocationTargetException;

import com.greentree.commons.util.classes.ClassUtil;

public record Injector(InjectionContainer container, DependencyScanner scanner) {
	
	public Injector {
		
	}
	
	public void inject(Object obj) {
		var deps = scanner.scan(obj);
		deps.forEach(x -> x.set(container));
	}
	
	public <T> T newInstace(Class<T> cls) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		var inst = ClassUtil.newInstance(cls);
		inject(inst);
		return inst;
	}
	
}
