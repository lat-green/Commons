package com.greentree.commons.data.resource.location;

import java.io.Serializable;

import com.greentree.commons.data.resource.Resource;

public interface ResourceLocation extends Serializable {
	
	default void clear() {
		throw new UnsupportedOperationException();
	}
	
	default ResourceLocation add(ResourceLocation location) {
		return MultiResourceLocation.EMPTY.builder().add(location).add(this).build();
	}
	
	Resource getResource(String name);
	
	default boolean isExist(String name) {
		return getResource(name) != null;
	}
	
}
