package com.greentree.commons.injector.factory;


public interface InstanceProperties {
	
	Iterable<? extends String> names();
	
	InstanceProperty get(String name);
	
}
