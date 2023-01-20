package com.greentree.data.resource.location;

import java.util.HashMap;
import java.util.Map;

import com.greentree.data.resource.Resource;

public interface NamedResourceLocation extends ResourceLocation {

	Iterable<String> getAllNames();

	default Map<String, Resource> toMap() {
		final var map = new HashMap<String, Resource>();
		for(var name : getAllNames()) map.put(name, getResource(name));
		return map;
	}

}
