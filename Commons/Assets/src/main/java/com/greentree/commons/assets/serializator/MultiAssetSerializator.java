package com.greentree.commons.assets.serializator;

import java.util.Objects;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.AssetKeyType;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.serializator.manager.DeepValidAssetManagerBase;
import com.greentree.commons.assets.serializator.manager.DefaultAssetManager;
import com.greentree.commons.assets.serializator.manager.ValidAssetManagerBase;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeUtil;
import com.greentree.commons.util.iterator.IteratorUtil;

public final class MultiAssetSerializator<T> implements AssetSerializator<T> {
	
	private final Iterable<AssetSerializator<T>> iterable;
	
	public MultiAssetSerializator(Iterable<AssetSerializator<T>> iterable) {
		this.iterable = iterable;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TypeInfo<T> getType() {
		if(IteratorUtil.isEmpty(iterable))
			return null;
		final var type = TypeUtil.lca(IteratorUtil.map(iterable, f->f.getType()));
		return (TypeInfo<T>) type;
	}
	
	@Override
	public String toString() {
		if(IteratorUtil.size(iterable) == 1)
			return iterable.iterator().next().toString();
		return "MultiAssetSerializator " + IteratorUtil.toString(iterable);
	}
	
	@Override
	public final Value<T> load(LoadContext context, AssetKey key) {
		for(var serializator : iterable)
			if(serializator.canLoad(context, key)) {
				final var v = serializator.load(context, key);
				return Objects.requireNonNull(v);
			}
		throw new IllegalArgumentException(
				"no one serializator can not load " + key + " " + IteratorUtil.toString(iterable));
	}
	
	@Override
	public final T loadDefault(DefaultAssetManager manager, AssetKeyType type) {
		for(var serializator : iterable) {
			final var v = serializator.loadDefault(manager, type);
			if(v != null)
				return v;
		}
		return null;
	}
	
	@Override
	public boolean isValid(ValidAssetManagerBase manager, AssetKey key) {
		return IteratorUtil.any(iterable, s-> {
			return s.isValid(manager, key);
		});
	}
	
	@Override
	public boolean isDeepValid(DeepValidAssetManagerBase manager, AssetKey key) {
		return IteratorUtil.any(iterable, s-> {
			return s.isDeepValid(manager, key);
		});
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return IteratorUtil.any(iterable, s-> {
			return s.canLoad(manager, key);
		});
	}
	
}
