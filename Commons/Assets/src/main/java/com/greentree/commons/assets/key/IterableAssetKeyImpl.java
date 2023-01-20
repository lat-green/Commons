package com.greentree.commons.assets.key;

import java.util.Iterator;

import com.greentree.commons.util.iterator.IteratorUtil;

public record IterableAssetKeyImpl(Iterable<AssetKey> keys) implements IterableAssetKey {

	public IterableAssetKeyImpl(AssetKey...keys) {
		this(IteratorUtil.iterable(keys));
	}
	
	@Override
	public Iterator<AssetKey> iterator() {
		return keys.iterator();
	}

}
