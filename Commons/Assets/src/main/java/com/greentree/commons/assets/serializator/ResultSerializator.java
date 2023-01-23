package com.greentree.commons.assets.serializator;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.ResultAssetKey;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.serializator.manager.ValidAssetManagerBase;
import com.greentree.commons.assets.value.ConstValue;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.util.classes.info.TypeInfo;

public final class ResultSerializator<T> extends TypedAssetSerializator<T> {
	
	public ResultSerializator(Class<T> cls) {
		super(cls);
	}
	
	public ResultSerializator(TypeInfo<T> type) {
		super(type);
	}
	
	@Override
	public boolean isValid(ValidAssetManagerBase manager, AssetKey key) {
		if(key instanceof ResultAssetKey res) {
			final var type = getType();
			return type.isInstance(res.result()) && (res.result() != null);
		}
		return false;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(key instanceof ResultAssetKey res) {
			final var type = getType();
			return type.isInstance(res.result());
		}
		return false;
	}
	
	@Override
	public Value<T> load(LoadContext context, AssetKey key) {
		if(key instanceof ResultAssetKey res) {
			final var type = getType();
			if(type.isInstance(res.result()))
				return ConstValue.newValue(res.getResult());
		}
		return null;
	}
	
}
