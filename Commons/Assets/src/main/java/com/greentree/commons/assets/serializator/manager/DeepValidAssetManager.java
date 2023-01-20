package com.greentree.commons.assets.serializator.manager;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;

public interface DeepValidAssetManager extends ValidAssetManager {
	
	default boolean isDeepValid(Class<?> cls, AssetKey key) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return isDeepValid(type, key);
	}
	
	default boolean isDeepValid(TypeInfo<?> type, AssetKey key) {
		return isValid(type, key);
	}
	
	
	
}
