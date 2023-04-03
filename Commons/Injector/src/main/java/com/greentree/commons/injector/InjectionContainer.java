package com.greentree.commons.injector;

public interface InjectionContainer {
	
	Object get(String name);
	
	<T> T get(String name, Class<T> cls);
	<T> T get(Class<T> cls);
	
}
