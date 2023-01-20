package com.greentree.commons.assets.key;

import java.io.Serializable;

public interface AssetKey extends Serializable {
	
	default AssetKeyType type() {
		return AssetKeyType.DEFAULT;
	}
	
}
