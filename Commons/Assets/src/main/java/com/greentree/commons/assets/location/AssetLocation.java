package com.greentree.commons.assets.location;

import com.greentree.commons.assets.key.AssetKey;

public interface AssetLocation {
	
	AssetKey getKey(String name);
	
	default boolean isExist(String name) {
		return getKey(name) != null;
	}
	
}
