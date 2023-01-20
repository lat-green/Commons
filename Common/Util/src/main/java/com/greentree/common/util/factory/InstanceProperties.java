package com.greentree.common.util.factory;


public interface InstanceProperties {
	
	Iterable<? extends String> names();
	
	InstanceProperty get(String name);
	
}
