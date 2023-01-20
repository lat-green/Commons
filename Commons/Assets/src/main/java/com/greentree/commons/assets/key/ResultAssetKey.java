package com.greentree.commons.assets.key;


public interface ResultAssetKey extends AssetKey {
	
	Object result();
	
	@SuppressWarnings("unchecked")
	default <T> T getResult() {
		return (T) result();
	}
	
}
