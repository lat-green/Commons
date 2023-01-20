package com.greentree.commons.assets.key;


public record NullAssetKey() implements ResultAssetKey {
	
	@Override
	public Object result() {
		return null;
	}
	
}
