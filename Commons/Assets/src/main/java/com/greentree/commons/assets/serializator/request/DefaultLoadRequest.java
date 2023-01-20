package com.greentree.commons.assets.serializator.request;

import com.greentree.commons.assets.key.AssetKeyType;
import com.greentree.commons.util.classes.info.TypeInfo;

public interface DefaultLoadRequest<T> {
	
	TypeInfo<T> loadType();
	
	AssetKeyType type();
	
}
