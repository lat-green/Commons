package com.greentree.commons.injector;


public interface Dependency {
	
	void set(Object host, InjectionContainer container);
	
}
