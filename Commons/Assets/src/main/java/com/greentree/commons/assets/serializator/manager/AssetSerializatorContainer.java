package com.greentree.commons.assets.serializator.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.function.Supplier;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.AssetKeyType;
import com.greentree.commons.assets.location.AssetLocation;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.DefaultSerializator;
import com.greentree.commons.assets.serializator.MultiAssetSerializator;
import com.greentree.commons.assets.serializator.NamedAssetSerializator;
import com.greentree.commons.assets.serializator.NullSerializator;
import com.greentree.commons.assets.serializator.ResourceAssetSerializator;
import com.greentree.commons.assets.serializator.ResultSerializator;
import com.greentree.commons.assets.serializator.TypedAssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.data.resource.location.ResourceLocation;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeUtil;
import com.greentree.commons.util.iterator.IteratorUtil;

final class AssetSerializatorContainer {
	
	private final Map<TypeInfo<?>, AssetSerializatorInfo<?>> serializators = new HashMap<>();
	
	private final Collection<Function<? super TypeInfo<?>, ? extends AssetSerializator<?>>> generators = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public AssetSerializatorContainer() {
		addGenerator(DefaultSerializator::new);
		addGenerator(ResultSerializator::new);
		addGenerator(NullSerializator::new);
	}
	
	public void addAssetLoction(AssetLocation location) {
		addGenerator(t->new NamedAssetSerializator<>(location, t));
	}
	
	public void addGenerator(
			Function<? super TypeInfo<?>, ? extends AssetSerializator<?>> generator) {
		generators.add(generator);
		synchronized(serializators) {
			for(var t : serializators.keySet())
				runGenerator(t, generator);
		}
	}
	
	public void addResourceLocation(ResourceLocation location) {
		addSerializator(new ResourceAssetSerializator(location));
	}
	
	public <T> void addSerializator(AssetSerializator<T> serializator) {
		final var type = serializator.getType();
		final var info = get(type);
		info.addSerializator(serializator);
	}
	
	@SuppressWarnings("unchecked")
	public <T> AssetSerializatorInfo<T> get(TypeInfo<T> type) {
		synchronized(serializators) {
			if(!serializators.containsKey(type)) {
				final var info = new AssetSerializatorInfo<>(type);
				for(var s : serializators.keySet()) {
					if(TypeUtil.isExtends(s, type)) {
						final var ss = (AssetSerializatorInfo<T>) serializators.get(s);
						ss.serializatorInfos.add(info);
					}
					if(TypeUtil.isExtends(type, s)) {
						final var ss = (AssetSerializatorInfo<T>) serializators.get(s);
						info.serializatorInfos.add(ss);
					}
				}
				serializators.put(type, info);
				onNewType(type);
				return info;
			}
			return (AssetSerializatorInfo<T>) serializators.get(type);
		}
	}
	
	private void onNewType(TypeInfo<?> type) {
		for(var g : generators)
			runGenerator(type, g);
	}
	
	private void runGenerator(TypeInfo<?> type,
			Function<? super TypeInfo<?>, ? extends AssetSerializator<?>> generator) {
		final var s = generator.apply(type);
		if(s != null)
			addSerializator(s);
	}
	
	public final class AssetSerializatorInfo<T> extends TypedAssetSerializator<T> {
		
		private final List<AssetSerializatorInfo<T>> serializatorInfos = new ArrayList<>();
		private final List<AssetSerializator<T>> serializators = new ArrayList<>();
		private final AssetSerializator<T> serializator = new MultiAssetSerializator<>(
				IteratorUtil.union(serializators, serializatorInfos));
		
		private final Ceche<AssetKey, Value<T>> cache = new Ceche<>();
		
		public AssetSerializatorInfo(TypeInfo<T> type) {
			super(type);
		}
		
		public void addSerializator(AssetSerializator<T> serializator) {
			serializators.add(serializator);
		}
		
		@Override
		public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
			if(cache.has(key))
				return true;
			return serializator.canLoad(manager, key);
		}
		
		@Override
		public boolean isDeepValid(DeepValidAssetManagerBase manager, AssetKey key) {
			final var value = cache.get(key);
			if(value != null)
				return !value.isNull();
			return serializator.isDeepValid(manager, key);
		}
		
		@Override
		public boolean isValid(ValidAssetManagerBase manager, AssetKey key) {
			final var value = cache.get(key);
			if(value != null)
				return !value.isNull();
			return serializator.isValid(manager, key);
		}
		
		@Override
		public Value<T> load(LoadContext context, AssetKey key) {
			return cache.set(key, ()-> {
				try {
					return serializator.load(context, key);
				}catch(Exception e) {
					throw new IllegalArgumentException("type:" + TYPE + " key:" + key, e);
				}
			});
		}
		
		@Override
		public T loadDefault(DefaultAssetManager manager, AssetKeyType asset_type) {
			return serializator.loadDefault(manager, asset_type);
		}
		
		@Override
		public String toString() {
			return "AssetSerializatorInfo [" + TYPE + "]";
		}
		
	}
	
	private static final class CachedValue<T> {
		
		private T value;
		
		public void set(Supplier<? extends T> value) {
			synchronized(this) {
				this.value = value.get();
			}
		}
		
		public T get() {
			synchronized(this) {
				return value;
			}
		}
		
	}
	
	private static final class Ceche<K, T> {
		
		private final ReadWriteLock lock = new ReentrantReadWriteLock();
		private final Map<K, CachedValue<T>> cache = new ConcurrentHashMap<>();
		
		public T get(K key) {
			lock.readLock().lock();
			try {
				final var value = cache.get(key);
				if(value != null)
					return value.get();
				return null;
			}finally {
				lock.readLock().unlock();
			}
		}
		
		public boolean has(K key) {
			return get(key) != null;
		}
		
		public T set(K key, Supplier<T> supplier) {
			lock.writeLock().lock();
			try {
				CachedValue<T> value = null;
				value = cache.get(key);
				if(value == null) {
					value = new CachedValue<>();
					cache.put(key, value);
					try {
						value.set(supplier);
					}catch(Exception e) {
						cache.remove(key);
						throw e;
					}
				}
				return value.get();
			}finally {
				lock.writeLock().unlock();
			}
		}
		
	}
}
