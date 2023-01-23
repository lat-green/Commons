package com.greentree.commons.assets.serializator.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.AssetKeyType;
import com.greentree.commons.assets.location.AssetLocation;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.LoadProperty;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.AssetSerializatorContainer.AssetSerializatorInfo;
import com.greentree.commons.assets.source.ConstSource;
import com.greentree.commons.assets.source.DefaultSource;
import com.greentree.commons.assets.source.NullSource;
import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.mapper.MutableWrappedSource;
import com.greentree.commons.data.resource.location.ResourceLocation;
import com.greentree.commons.util.classes.info.TypeInfo;

public final class AssetManager implements AssetManagerBase, AsyncAssetManager,
		ValidAssetManagerBase, DeepValidAssetManagerBase {
	
	private final AssetSerializatorContainer container = new AssetSerializatorContainer();
	
	private final ExecutorService executor;
	
	
	public ExecutorService executor() {
		return executor;
	}
	
	public AssetManager() {
		executor = Executors.newSingleThreadExecutor(r-> {
			final var thread = new Thread(r, "AssetManager");
			thread.setDaemon(true);
			thread.setPriority(Thread.MAX_PRIORITY);
			return thread;
		});
	}
	
	public AssetManager(ExecutorService executor) {
		this.executor = executor;
	}
	
	public void addAssetLoction(AssetLocation location) {
		container.addAssetLoction(location);
	}
	
	public void addGenerator(
			Function<? super TypeInfo<?>, ? extends AssetSerializator<?>> generator) {
		container.addGenerator(generator);
	}
	
	public void addResourceLocation(ResourceLocation location) {
		container.addResourceLocation(location);
	}
	
	public <T> void addSerializator(AssetSerializator<T> serializator) {
		container.addSerializator(serializator);
	}
	
	@Override
	public boolean canLoad(TypeInfo<?> type, AssetKey key) {
		final var info = get(type);
		return info.canLoad(this, key);
	}
	
	@Override
	public boolean isDeepValid(TypeInfo<?> type, AssetKey key) {
		final var info = get(type);
		return info.isDeepValid(this, key);
	}
	
	@Override
	public boolean isValid(TypeInfo<?> type, AssetKey key) {
		final var info = get(type);
		return info.isValid(this, key);
	}
	
	@Override
	public <T> Source<T> load(TypeInfo<T> type, AssetKey key, T def) {
		return load(type, key, def, 0);
	}
	
	public <T> Source<T> load(TypeInfo<T> type, AssetKey key, T def, int properties) {
		final var wrapper = new LoadContextImpl(properties);
		final var result = wrapper.load(type, key, def);
		return result;
	}
	
	public <T> Source<T> load(TypeInfo<T> type, AssetKey key, T def, LoadProperty... properties) {
		final var mask = LoadProperty.getMask(properties);
		return load(type, key, def, mask);
	}
	
	@Override
	public <T> Source<T> loadAsync(TypeInfo<T> type, AssetKey key, T def) {
		return load(type, key, def, LoadProperty.LOAD_ASYNC);
	}
	
	@Override
	public <T> T loadData(TypeInfo<T> type, AssetKey key, T def) {
		final var v = load(type, key, def, LoadProperty.LOAD_CONST);
		return v.get();
	}
	
	@Override
	public <T> T loadDefault(TypeInfo<T> type, AssetKeyType asset_type) {
		final var info = get(type);
		return info.loadDefault(this, asset_type);
	}
	
	private <T> AssetSerializatorInfo<T> get(TypeInfo<T> type) {
		return container.get(type);
	}
	
	private final class LoadContextImpl implements LoadContext {
		
		private final LoadContextImpl parent;
		private final int properties;
		
		public LoadContextImpl(int properties) {
			this.properties = properties;
			final var parent_properties = LoadProperty.getSubAssetMask(properties);
			if(parent_properties == properties)
				parent = this;
			else
				parent = new LoadContextImpl(parent_properties);
		}
		
		@Override
		public boolean canLoad(TypeInfo<?> type, AssetKey key) {
			return AssetManager.this.canLoad(type, key);
		}
		
		public boolean has(LoadProperty property) {
			return LoadProperty.has(properties, property);
		}
		
		@Override
		public boolean isDeepValid(TypeInfo<?> type, AssetKey key) {
			return AssetManager.this.isDeepValid(type, key);
		}
		
		@Override
		public boolean isValid(TypeInfo<?> type, AssetKey key) {
			return AssetManager.this.isValid(type, key);
		}
		
		@Override
		public <T> Source<T> load(TypeInfo<T> type, AssetKey key, T def) {
			final var result = tryLoadAsync(type, key, def);
			if(def == null)
				return result;
			return DefaultSource.newValue(result, ConstSource.newValue(def));
		}
		
		@Override
		public <T> T loadDefault(TypeInfo<T> type, AssetKeyType asset_type) {
			return AssetManager.this.loadDefault(type, asset_type);
		}
		
		@Override
		public ParallelAssetManger parallel() {
			return new ParallelAssetMangerImpl();
		}
		
		@Override
		public String toString() {
			return "PropWrapAssetLoader " + Arrays.toString(LoadProperty.getArray(properties));
		}
		
		private <T> Source<T> loadForward(TypeInfo<T> type, AssetKey key, T def) {
			final var info = get(type);
			try {
				final var v = info.load(parent, key);
				return v;
			}catch(Exception e) {
				if(def != null) {
					e.printStackTrace();
					return ConstSource.newValue(def);
				}
				throw e;
			}
		}
		
		private <T> Source<T> tryLoadAsync(TypeInfo<T> type, AssetKey key, T in_def) {
			if(has(LoadProperty.LOAD_ASYNC)) {
				final var info = get(type);
				
				final T def;
				if(in_def == null)
					def = info.loadDefault(this, key.type());
				else
					def = in_def;
				
				
				if(def != null || has(LoadProperty.NULLABLE)) {
					final var async_result = new MutableWrappedSource<>(ConstSource.newValue(def));
					executor.execute(()-> {
						try {
							final var v = loadForward(type, key, def);
							if(v == null)
								throw new RuntimeException("TYPE: " + info.TYPE);
							async_result.set(v);
						}catch(RuntimeException e) {
							e.printStackTrace();
						}
					});
					return async_result;
				}
			}
			final var result = loadForward(type, key, in_def);
			return result;
		}
		
		public final class ParallelAssetMangerImpl implements ParallelAssetManger {
			
			private final Collection<Future<?>> tasks = new ArrayList<>();
			
			
			public ParallelAssetMangerImpl() {
			}
			
			@Override
			public boolean canLoad(TypeInfo<?> type, AssetKey key) {
				return LoadContextImpl.this.canLoad(type, key);
			}
			
			@Override
			public void close() {
				for(var task : tasks)
					try {
						task.get();
					}catch(InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
			}
			
			@Override
			public <T> Source<T> load(TypeInfo<T> type, AssetKey key, T def) {
				final var result = new MutableWrappedSource<>(NullSource.<T>instance());
				final var task = executor.submit(()-> {
					final var v = LoadContextImpl.this.load(type, key, def);
					result.set(v);
				});
				tasks.add(task);
				return result;
			}
			
			@Override
			public <T> T loadDefault(TypeInfo<T> type, AssetKeyType asset_type) {
				return LoadContextImpl.this.loadDefault(type, asset_type);
			}
			
		}
		
	}
	
}
