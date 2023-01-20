package com.greentree.data.resource;

import java.io.IOException;
import java.io.InputStream;

import com.greentree.data.resource.location.ClassLoaderResourceLocation;
import com.greentree.data.resource.location.ResourceLocation;
import com.greentree.data.resource.location.MultiResourceLocation;

@Deprecated
public class StaticResourceLoader {

	public static MultiResourceLocation LOCATION = MultiResourceLocation.EMPTY;

	static {
		addResourceLocation(new ClassLoaderResourceLocation(StaticResourceLoader.class));
	}

	public static void addResourceLocation(ResourceLocation... location) {
		final var builder = LOCATION.builder();
		for(var loc : location) builder.add(loc);
		LOCATION = builder.build();
	}

	public static ResourceLocation getLocation() {
		return LOCATION;
	}
	public static Resource getResource(final String ref) {
		return LOCATION.getResource(ref);
	}

	/**
	 * @deprecated use getResource(name).open()
	 */
	@Deprecated
	public static InputStream openInputResourceStream(String string) throws IOException {
		return LOCATION.getResource(string).open();
	}

}
