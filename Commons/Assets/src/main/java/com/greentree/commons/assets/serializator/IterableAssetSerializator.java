package com.greentree.commons.assets.serializator;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.IterableAssetKey;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.merge.MIValue;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.iterator.IteratorUtil;

public final class IterableAssetSerializator<T> extends AbstractIterableAssetSerializator<T> {
	
	public IterableAssetSerializator(Class<T> cls) {
		super(cls);
	}
	
	public IterableAssetSerializator(TypeInfo<T> type) {
		super(type);
	}
	
	public Value<Iterable<T>> load(LoadContext context, AssetKey ckey) {
		if(ckey instanceof IterableAssetKey key) {
			final var iter = IteratorUtil.clone(IteratorUtil.map(key, k-> {
				return context.load(ITER_TYPE, k);
			}));
			
			return new MIValue<>(iter);
		}
		return null;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(key instanceof IterableAssetKey k) {
			for(var s : k)
				if(!manager.canLoad(ITER_TYPE, s))
					return false;
			return true;
		}
		return false;
	}
	
}
