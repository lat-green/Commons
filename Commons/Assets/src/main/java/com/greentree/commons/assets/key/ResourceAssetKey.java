package com.greentree.commons.assets.key;

import java.util.Objects;

public record ResourceAssetKey(AssetKey resourceName) implements AssetKey {
	
	
	public ResourceAssetKey {
		Objects.requireNonNull(resourceName);
	}
	
	public ResourceAssetKey(String resourceName) {
		this(new ResultAssetKeyImpl(resourceName));
	}
	
	@Override
	public String toString() {
		return "Resource[" + resourceName + "]";
	}
	
}
