package com.greentree.commons.data.resource.location;

import com.greentree.commons.data.resource.InMemoryResource;

public final class InMemoryResourceLocation extends MapIOResourceLocation<InMemoryResource>
		implements IOResourceLocation {
	
	private static final long serialVersionUID = 1L;
	
	public InMemoryResourceLocation() {
	}
	
	public InMemoryResourceLocation(Iterable<? extends InMemoryResource> resources) {
		super(resources);
	}
	
	@Override
	protected InMemoryResource newResource(String name) {
		return new InMemoryResource(name);
	}
	
	
	
	
}
