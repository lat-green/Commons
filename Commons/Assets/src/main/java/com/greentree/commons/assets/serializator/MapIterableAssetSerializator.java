package com.greentree.commons.assets.serializator;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.merge.MISource;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.commons.util.iterator.IteratorUtil;

public final class MapIterableAssetSerializator<T, R> implements AssetSerializator<Iterable<R>> {
	
	private final TypeInfo<T> T_TYPE;
	private final TypeInfo<R> R_TYPE;
	
	private final TypeInfo<Iterable<T>> T_ITER_TYPE;
	private final TypeInfo<Iterable<R>> R_ITER_TYPE;
	
	
	public MapIterableAssetSerializator(TypeInfo<T> t, TypeInfo<R> r) {
		T_TYPE = t;
		R_TYPE = r;
		T_ITER_TYPE = TypeInfoBuilder.getTypeInfo(Iterable.class, T_TYPE);
		R_ITER_TYPE = TypeInfoBuilder.getTypeInfo(Iterable.class, R_TYPE);
	}
	
	@Override
	public TypeInfo<Iterable<R>> getType() {
		return R_ITER_TYPE;
	}
	
	@Override
	public Source<Iterable<R>> load(LoadContext context, AssetKey ckey) {
		if(context.canLoad(T_ITER_TYPE, ckey)) {
			final var t_iter = context.load(T_ITER_TYPE, ckey);
			final var rv_iter = IteratorUtil.clone(IteratorUtil.map(t_iter.get(), t-> {
				return context.load(R_TYPE, t);
			}));
			return new MISource<>(rv_iter);
		}
		return null;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(manager.canLoad(T_ITER_TYPE, key)) {
			return true;
		}
		return false;
	}
	
}
