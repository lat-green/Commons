package com.greentree.commons.injector;


public interface ConfigInjectionContainer extends InjectionContainer {
	
	
	void put(String name, Object value);
	void put(Object value);
	
	void putPriority(String name, Object value);
	void putPriority(Object value);
	
}
