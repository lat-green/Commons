package com.greentree.common.assets.file;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;


public final class StringToIntAssetSerializator implements AssetSerializator<Integer> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(String.class, key);
	}
	
	@Override
	public Value<Integer> load(LoadContext context, AssetKey key) {
		if(context.canLoad(String.class, key)) {
			final var str = context.load(String.class, key);
			return context.map(str, new StringToInt());
		}
		return null;
	}
	
	private static final class StringToInt implements Value1Function<String, Integer> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public Integer apply(String value) {
			return Integer.parseInt(value);
		}
		
	}
	
}
