package com.greentree.commons.assets.location;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.util.iterator.IteratorUtil;


public final class MultiAssetLocation implements AssetLocation {
	
	private final Iterable<? extends AssetLocation> locations;
	
	private MultiAssetLocation(Iterable<? extends AssetLocation> locations) {
		this.locations = locations;
	}
	
	public static AssetLocation create(AssetLocation... locations) {
		return create(IteratorUtil.iterable(locations));
	}
	
	public static AssetLocation create(Iterable<? extends AssetLocation> locations) {
		synchronized(locations) {
			final var size = IteratorUtil.size(locations);
			if(size == 0)
				throw new IllegalArgumentException("empty locations");
			if(size == 1)
				return locations.iterator().next();
			return create(locations);
		}
	}
	
	@Override
	public AssetKey getKey(String name) {
		for(var loc : locations)
			if(loc.isExist(name)) {
				return loc.getKey(name);
			}
		return null;
	}
	
	@Override
	public boolean isExist(String name) {
		for(var loc : locations)
			if(loc.isExist(name))
				return true;
		return false;
	}
	
}
