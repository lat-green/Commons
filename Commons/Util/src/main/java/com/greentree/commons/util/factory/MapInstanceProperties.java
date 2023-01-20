package com.greentree.commons.util.factory;

import java.util.Map;

public final class MapInstanceProperties implements InstanceProperties {
	
	private final Map<? extends String, ? extends InstanceProperty> properties;
	
	public MapInstanceProperties(Map<? extends String, ? extends InstanceProperty> properties) {
		this.properties = properties;
	}
	
	@Override
	public InstanceProperty get(String name) {
		return properties.get(name);
	}
	
	@Override
	public Iterable<? extends String> names() {
		return properties.keySet();
	}
	
	@Override
	public String toString() {
		return "MapInstanceProperties [" + properties + "]";
	}
	
}
