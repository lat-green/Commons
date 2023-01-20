package com.greentree.commons.data.resource.location;

import com.greentree.commons.data.resource.IOResource;

public interface IOResourceLocation extends ResourceLocation {
	
	@Override
	IOResource getResource(String name);
	
	
	IOResource createNewResource(String name);
	
	default boolean deleteResource(String name) {
		final var res = getResource(name);
		if(res == null)
			return false;
		return res.delete();
	}
	
	default IOResource createResource(String name) {
		if(isExist(name))
			deleteResource(name);
		return createResource(name);
	}
	
}
