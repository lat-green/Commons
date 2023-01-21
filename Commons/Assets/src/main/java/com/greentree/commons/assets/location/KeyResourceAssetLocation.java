package com.greentree.commons.assets.location;

import java.io.IOException;
import java.io.ObjectInputStream;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.data.resource.location.ResourceLocation;
import com.greentree.commons.util.exception.MultiException;
import com.greentree.commons.util.exception.WrappedException;


public class KeyResourceAssetLocation implements AssetLocation {
	
	private final ResourceLocation resources;
	
	public KeyResourceAssetLocation(ResourceLocation resources) {
		this.resources = resources;
	}
	
	private static AssetKey get(Resource res) throws IOException, ClassNotFoundException {
		try(final var in = res.open()) {
			try(final var oin = new ObjectInputStream(in)) {
				return (AssetKey) oin.readObject();
			}
		}
	}
	
	@Override
	public AssetKey getKey(String name) {
		AssetKey key = null;
		try {
			key = get0(name + ".key");
		}catch(Exception e1) {
			try {
				key = get0(name);
			}catch(Exception e2) {
				throw new MultiException(e1, e2);
			}
		}
		if(key == null)
			try {
				key = get0(name);
			}catch(Exception e) {
				throw new WrappedException(e);
			}
		return key;
	}
	
	public ResourceLocation getResources() {
		return resources;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("KeyResourceAssetLocation [resources=");
		builder.append(resources);
		builder.append("]");
		return builder.toString();
	}
	
	private AssetKey get0(String name) throws ClassNotFoundException, IOException {
		final var res = resources.getResource(name);
		if(res == null)
			return null;
		return get(res);
	}
	
}
