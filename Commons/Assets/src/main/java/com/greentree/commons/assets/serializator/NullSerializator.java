package com.greentree.commons.assets.serializator;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.NullAssetKey;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.serializator.manager.ValidAssetManagerBase;
import com.greentree.commons.assets.source.NullSource;
import com.greentree.commons.assets.source.Source;
import com.greentree.commons.util.classes.info.TypeInfo;

public final class NullSerializator<T> extends TypedAssetSerializator<T> {
	
	public NullSerializator(Class<T> cls) {
		super(cls);
	}
	
	public NullSerializator(TypeInfo<T> type) {
		super(type);
	}
	
	@Override
	public boolean isValid(ValidAssetManagerBase manager, AssetKey key) {
		return false;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(key instanceof NullAssetKey)
			return true;
		return false;
	}
	
	@Override
	public Source<T> load(LoadContext context, AssetKey key) {
		if(key instanceof NullAssetKey)
			return NullSource.instance();
		return null;
	}
	
}
