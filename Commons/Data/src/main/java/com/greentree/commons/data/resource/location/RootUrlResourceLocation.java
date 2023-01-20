package com.greentree.commons.data.resource.location;

import java.net.MalformedURLException;
import java.net.URL;

import com.greentree.commons.data.resource.URLResource;


public final class RootUrlResourceLocation implements ResourceLocation {
	
	private static final long serialVersionUID = 1L;
	private final URL context;
	
	public RootUrlResourceLocation(URL context) {
		this.context = context;
	}
	
	@Override
	public URLResource getResource(String name) {
		URL url;
		try {
			url = new URL(context, name);
		}catch(MalformedURLException e) {
			throw new RuntimeException(e);
		}
		return new URLResource(url);
	}
	
}
