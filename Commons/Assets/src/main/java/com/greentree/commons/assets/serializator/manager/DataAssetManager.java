package com.greentree.commons.assets.serializator.manager;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.request.KeyLoadRequest;
import com.greentree.commons.assets.serializator.request.builder.KeyRequestBuilder;
import com.greentree.commons.assets.serializator.request.builder.KeyRequestBuilderImpl;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;

public interface DataAssetManager {
	
	default <T> T loadData(KeyLoadRequest<T> request) {
		return loadData(request.loadType(), request.key(), request.getDefault());
	}
	
	default <T> KeyRequestBuilder<T, T> loadData(TypeInfo<T> type) {
		return new KeyRequestBuilderImpl<>(r->loadData(r), type);
	}
	
	default <T> KeyRequestBuilder<T, T> loadData(Class<T> cls) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return loadData(type);
	}
	
	default <T> T loadData(Class<T> cls, AssetKey key) {
		return loadData(cls, key, null);
	}
	
	default <T> T loadData(Class<T> cls, AssetKey key, T def) {
		return loadData(TypeInfoBuilder.getTypeInfo(cls), key, def);
	}
	
	default <T> T loadData(TypeInfo<T> type, AssetKey key) {
		return loadData(type, key, null);
	}
	
	<T> T loadData(TypeInfo<T> type, AssetKey key, T def);
	
}
