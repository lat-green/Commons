package com.greentree.commons.assets.key;


public record ResultAssetKeyImpl(Object result) implements ResultAssetKey {

	@Override
	public String toString() {
		return result.toString();
	}
	
	
	
}
