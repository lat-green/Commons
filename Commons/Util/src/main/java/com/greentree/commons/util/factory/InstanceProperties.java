package com.greentree.commons.util.factory;


public interface InstanceProperties {
	
	Iterable<? extends String> names();
	
	InstanceProperty get(String name);
	
}
