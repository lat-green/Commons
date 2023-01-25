package com.greentree.commons.assets.source.provider;

import java.util.Objects;
import java.util.function.Consumer;

import com.greentree.commons.data.resource.Resource;

public final class ResourceProvider implements SourceProvider<Resource> {
	
	public static final int CHARACTERISTICS = BLANCK_CLOSE | NOT_NULL;
	private long lastModified;
	private final Resource resource;
	
	
	public ResourceProvider(Resource resource) {
		Objects.requireNonNull(resource);
		this.resource = resource;
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public boolean tryGet(Consumer<? super Resource> action) {
		final var lastModified = resource.lastModified();
		if(lastModified > this.lastModified) {
			this.lastModified = lastModified;
			action.accept(resource);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isChenge() {
		final var lastModified = resource.lastModified();
		return lastModified > this.lastModified;
	}
	
	@Override
	public Resource get() {
		lastModified = resource.lastModified();
		return resource;
	}
	
	@Override
	public void close() {
	}
	
}
