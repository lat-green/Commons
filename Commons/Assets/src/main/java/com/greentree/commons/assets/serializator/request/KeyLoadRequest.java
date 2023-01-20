package com.greentree.commons.assets.serializator.request;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.AssetKeyType;

public interface KeyLoadRequest<T> extends DefaultLoadRequest<T> {
	
	AssetKey key();
	
	@Override
	default AssetKeyType type() {
		return key().type();
	}

	T getDefault();
	
}
