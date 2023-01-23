package com.greentree.commons.assets.serializator;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.DefaultAssetKey;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.serializator.manager.DeepValidAssetManagerBase;
import com.greentree.commons.assets.serializator.manager.ValidAssetManagerBase;
import com.greentree.commons.assets.value.DefaultValue;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.iterator.IteratorUtil;

public final class DefaultSerializator<T> extends TypedAssetSerializator<T> {
	
	public DefaultSerializator(Class<T> cls) {
		super(cls);
	}
	
	public DefaultSerializator(TypeInfo<T> type) {
		super(type);
	}
	
	@Override
	public boolean isDeepValid(DeepValidAssetManagerBase manager, AssetKey key) {
		if(key instanceof DefaultAssetKey ks) {
			for(var k : ks.keys())
				if(manager.isDeepValid(TYPE, k))
					return true;
			return false;
		}
		return false;
	}
	
	@Override
	public boolean isValid(ValidAssetManagerBase manager, AssetKey key) {
		if(key instanceof DefaultAssetKey ks) {
			for(var k : ks.keys())
				if(manager.isValid(TYPE, k))
					return true;
			return false;
		}
		return false;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(key instanceof DefaultAssetKey ks) {
			return true;
		}
		return false;
	}
	
	@Override
	public Value<T> load(LoadContext context, AssetKey key) {
		if(key instanceof DefaultAssetKey ks) {
			final var keys = ks.keys();
			final var values = IteratorUtil.map(keys, k->context.load(TYPE, k));
			return DefaultValue.newValue(values);
		}
		return null;
	}
	
}
