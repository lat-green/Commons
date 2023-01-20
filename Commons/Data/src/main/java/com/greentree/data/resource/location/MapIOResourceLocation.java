package com.greentree.data.resource.location;

import java.util.HashMap;
import java.util.Map;

import com.greentree.data.resource.IOResource;

public abstract class MapIOResourceLocation<R extends IOResource> implements IOResourceLocation {
	
	private static final long serialVersionUID = 1L;
	
	private final Map<String, R> resources = new HashMap<>();
	
	
	public MapIOResourceLocation() {
	}
	
	public MapIOResourceLocation(Iterable<? extends R> resources) {
		for(var res : resources)
			this.resources.put(res.getName(), res);
	}
	
	@Override
	public final synchronized R getResource(String name) {
		if(!resources.containsKey(name))
			return null;
		final var res = resources.get(name);
		return res;
	}
	
	@Override
	public final synchronized R createNewResource(String name) {
		if(!resources.containsKey(name))
			throw new IllegalArgumentException("already created " + name);
		final var res = newResource(name);
		resources.put(name, res);
		return res;
	}
	
	protected abstract R newResource(String name);
	
	
	
}
