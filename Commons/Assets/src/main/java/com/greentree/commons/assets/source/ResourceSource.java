package com.greentree.commons.assets.source;

import java.util.Objects;

import com.greentree.commons.assets.source.provider.ResourceSupplier;
import com.greentree.commons.assets.source.provider.SourceProvider;
import com.greentree.commons.data.resource.Resource;

public final class ResourceSource implements Source<Resource> {
	
	private static final long serialVersionUID = 1L;
	
	private final Resource resource;
	
	public ResourceSource(Resource resource) {
		Objects.requireNonNull(resource);
		this.resource = resource;
	}
	
	@Override
	public String toString() {
		return "Resource [" + resource + "]";
	}
	
	@Override
	public SourceProvider<Resource> openProvider() {
		return new ResourceSupplier(resource);
	}
	
	@Override
	public int characteristics() {
		return ResourceSupplier.CHARACTERISTICS;
	}
	
}
