package com.greentree.commons.assets.serializator.manager;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.ResourceAssetKey;
import com.greentree.commons.assets.key.ResultAssetKeyImpl;
import com.greentree.commons.assets.serializator.request.KeyLoadRequest;
import com.greentree.commons.assets.serializator.request.builder.KeyRequestBuilder;
import com.greentree.commons.assets.serializator.request.builder.KeyRequestBuilderImpl;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;

public interface ValueAssetManager extends DefaultAssetManager {
	
	default <T> KeyRequestBuilder<T, Value<T>> load(TypeInfo<T> type) {
		return new KeyRequestBuilderImpl<>(r->load(r), type);
	}
	
	default <T> KeyRequestBuilder<T, Value<T>> load(Class<T> cls) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return load(type);
	}
	
	default <T> Value<T> load(Class<T> cls, AssetKey key) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return load(type, key, null);
	}
	
	default <T> Value<T> load(Class<T> cls, AssetKey key, T def) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return load(type, key, def);
	}
	
	default <T> Value<T> load(Class<T> cls, Object key) {
		return load(cls, new ResultAssetKeyImpl(key), null);
	}
	
	default <T> Value<T> load(Class<T> cls, Object key, T def) {
		return load(cls, new ResultAssetKeyImpl(key), def);
	}
	
	default <T> Value<T> load(Class<T> cls, String key) {
		return load(cls, new ResourceAssetKey(key), null);
	}
	
	default <T> Value<T> load(Class<T> cls, String key, T def) {
		return load(cls, new ResourceAssetKey(key), def);
	}
	
	default <T> Value<T> load(TypeInfo<T> type, AssetKey key) {
		return load(type, key, null);
	}
	
	<T> Value<T> load(TypeInfo<T> type, AssetKey key, T def);
	
	default <T> Value<T> load(KeyLoadRequest<T> request) {
		return load(request.loadType(), request.key(), request.getDefault());
	}
	
	default <T> Value<T> load(TypeInfo<T> type, Object key) {
		return load(type, new ResultAssetKeyImpl(key), null);
	}
	
	default <T> Value<T> load(TypeInfo<T> type, Object key, T def) {
		return load(type, new ResultAssetKeyImpl(key), def);
	}
	
	default <T> Value<T> load(TypeInfo<T> type, String key) {
		return load(type, new ResourceAssetKey(key), null);
	}
	
	default <T> Value<T> load(TypeInfo<T> type, String key, T def) {
		return load(type, new ResourceAssetKey(key), def);
	}
	
}
