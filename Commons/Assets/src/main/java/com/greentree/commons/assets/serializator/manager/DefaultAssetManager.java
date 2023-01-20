package com.greentree.commons.assets.serializator.manager;

import com.greentree.commons.assets.key.AssetKeyType;
import com.greentree.commons.assets.serializator.request.DefaultLoadRequest;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;

public interface DefaultAssetManager
		extends CanLoadAssetManager, ValidAssetManager, DeepValidAssetManager {
	
	default <T> T loadDefault(Class<T> cls) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return loadDefault(type);
	}
	
	default <T> T loadDefault(Class<T> cls, AssetKeyType asset_type) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return loadDefault(type, asset_type);
	}
	
	default <T> T loadDefault(Class<T> cls, AssetKeyType asset_type, T def) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return loadDefault(type, asset_type, def);
	}
	
	default <T> T loadDefault(Class<T> cls, T def) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return loadDefault(type, def);
	}
	
	default <T> T loadDefault(DefaultLoadRequest<T> request) {
		return loadDefault(request.loadType(), request.type());
	}
	
	default <T> T loadDefault(DefaultLoadRequest<T> request, T def) {
		return loadDefault(request.loadType(), request.type(), def);
	}
	
	default <T> T loadDefault(TypeInfo<T> type) {
		return loadDefault(type, AssetKeyType.DEFAULT);
	}
	
	<T> T loadDefault(TypeInfo<T> type, AssetKeyType asset_type);
	
	default <T> T loadDefault(TypeInfo<T> type, AssetKeyType asset_type, T def) {
		final var v = loadDefault(type, asset_type);
		if(v == null)
			return def;
		return v;
	}
	
	default <T> T loadDefault(TypeInfo<T> type, T def) {
		return loadDefault(type, AssetKeyType.DEFAULT, def);
	}
	
}
