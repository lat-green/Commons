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

public interface AsyncAssetManager extends ValueAssetManager {
	
	default <T> Value<T> loadAsync(KeyLoadRequest<T> request) {
		return loadAsync(request.loadType(), request.key(), request.getDefault());
	}
	
	default <T> KeyRequestBuilder<T, Value<T>> loadAsync(TypeInfo<T> type) {
		return new KeyRequestBuilderImpl<>(r->loadAsync(r), type);
	}
	
	default <T> KeyRequestBuilder<T, Value<T>> loadAsync(Class<T> cls) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return loadAsync(type);
	}
	
	default <T> Value<T> loadAsync(Class<T> cls, AssetKey key) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return loadAsync(type, key, loadDefault(type));
	}
	
	default <T> Value<T> loadAsync(Class<T> cls, AssetKey key, T def) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return loadAsync(type, key, loadDefault(type, def));
	}
	
	default <T> Value<T> loadAsync(TypeInfo<T> type, AssetKey key) {
		return loadAsync(type, key, loadDefault(type));
	}
	
	<T> Value<T> loadAsync(TypeInfo<T> type, AssetKey key, T def);
	
	default <T> Value<T> loadAsync(Class<T> cls, String path) {
		return loadAsync(cls, new ResourceAssetKey(path), loadDefault(cls));
	}
	
	default <T> Value<T> loadAsync(Class<T> cls, String path, T def) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return loadAsync(type, new ResourceAssetKey(path), loadDefault(type, def));
	}
	
	default <T> Value<T> loadAsync(TypeInfo<T> type, String path) {
		return loadAsync(type, new ResourceAssetKey(path), loadDefault(type));
	}
	
	default <T> Value<T> loadAsync(TypeInfo<T> type, String path, T def) {
		return loadAsync(type, new ResourceAssetKey(path), def);
	}
	
	default <T> Value<T> loadAsync(Class<T> cls, Object obj) {
		return loadAsync(cls, new ResultAssetKeyImpl(obj), loadDefault(cls));
	}
	
	default <T> Value<T> loadAsync(Class<T> cls, Object obj, T def) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return loadAsync(type, new ResultAssetKeyImpl(obj), loadDefault(type, def));
	}
	
	default <T> Value<T> loadAsync(TypeInfo<T> type, Object obj) {
		return loadAsync(type, new ResultAssetKeyImpl(obj), loadDefault(type));
	}
	
	default <T> Value<T> loadAsync(TypeInfo<T> type, Object obj, T def) {
		return loadAsync(type, new ResultAssetKeyImpl(obj), def);
	}
	
}
