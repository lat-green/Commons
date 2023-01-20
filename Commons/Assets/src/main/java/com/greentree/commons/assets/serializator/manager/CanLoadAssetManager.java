package com.greentree.commons.assets.serializator.manager;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;

public interface CanLoadAssetManager extends ValidAssetManager {
	
	@Override
	default boolean isValid(TypeInfo<?> type, AssetKey key) {
		return canLoad(type, key);
	}
	
	default boolean canLoad(Class<?> cls, AssetKey key) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return canLoad(type, key);
	}
	
	boolean canLoad(TypeInfo<?> type, AssetKey key);
	
}
