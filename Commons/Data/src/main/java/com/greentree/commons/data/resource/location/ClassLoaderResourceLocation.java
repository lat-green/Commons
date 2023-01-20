package com.greentree.commons.data.resource.location;

import java.net.URL;
import java.util.Objects;

import com.greentree.commons.data.resource.URLResource;

public class ClassLoaderResourceLocation implements ResourceLocation {
	private static final long serialVersionUID = 1L;
	
	private final Class<?> cls;

	public ClassLoaderResourceLocation() {
		this(ClassLoaderResourceLocation.class);
	}
	public ClassLoaderResourceLocation(Class<?> cls) {
		this.cls = cls;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		ClassLoaderResourceLocation other = (ClassLoaderResourceLocation) obj;
		return Objects.equals(cls, other.cls);
	}

	public ClassLoader getLoader() {
		return cls.getClassLoader();
	}

	@Override
	public URLResource getResource(String name) {
		final var url = getURL(name);
		if(url == null) return null;
		return new URLResource(url);
	}

	public URL getURL(String name) {
		if(name.isBlank()) return null;
		return getLoader().getResource(name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cls);
	}

	@Override
	public String toString() {
		return "ClassLoaderResourceLocation [" + cls + "]";
	}



}
