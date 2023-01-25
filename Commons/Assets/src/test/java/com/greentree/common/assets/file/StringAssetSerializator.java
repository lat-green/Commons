package com.greentree.common.assets.file;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.AssetKeyType;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.serializator.manager.DefaultAssetManager;
import com.greentree.commons.assets.value.ConstValue;
import com.greentree.commons.assets.value.Value;


public final class StringAssetSerializator implements AssetSerializator<String> {
	
	private final String DEF_STR;
	
	public StringAssetSerializator(String dEF_STR) {
		DEF_STR = dEF_STR;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return key instanceof StringAssetKey;
	}
	
	@Override
	public Value<String> load(LoadContext context, AssetKey key) {
		try {
			Thread.sleep(20);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		if(key instanceof StringAssetKey obj)
			return ConstValue.newValue(obj.value());
		return null;
	}
	
	@Override
	public String loadDefault(DefaultAssetManager manager, AssetKeyType type) {
		return DEF_STR;
	}
	
}
