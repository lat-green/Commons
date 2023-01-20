package com.greentree.commons.assets.serializator.request.builder;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.ResourceAssetKey;
import com.greentree.commons.assets.key.ResultAssetKeyImpl;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;

public interface KeyRequestBuilder<T, R> extends RequestBuilder<R> {
	
	default KeyRequestBuilder<T, R> set(Class<T> cls) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return set(type);
	}
	
	default KeyRequestBuilder<T, R> setResource(String resource) {
		final var key = new ResourceAssetKey(resource);
		return set(key);
	}
	
	default KeyRequestBuilder<T, R> set(Object resultValue) {
		final var key = new ResultAssetKeyImpl(resultValue);
		return set(key);
	}
	
	KeyRequestBuilder<T, R> set(AssetKey key);
	KeyRequestBuilder<T, R> setDefault(T def);
	
}
