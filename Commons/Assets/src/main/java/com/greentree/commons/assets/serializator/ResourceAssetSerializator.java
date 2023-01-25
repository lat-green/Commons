package com.greentree.commons.assets.serializator;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.ResourceAssetKey;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.serializator.manager.DeepValidAssetManagerBase;
import com.greentree.commons.assets.source.ResourceSource;
import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.provider.ReduceProvider;
import com.greentree.commons.assets.source.provider.SourceProvider;
import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.data.resource.location.ResourceLocation;


public class ResourceAssetSerializator implements AssetSerializator<Resource> {
	
	
	
	private final ResourceLocation resources;
	
	public ResourceAssetSerializator(ResourceLocation resources) {
		this.resources = resources;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(key instanceof ResourceAssetKey k)
			return manager.canLoad(String.class, k.resourceName());
		return false;
	}
	
	@Override
	public boolean isDeepValid(DeepValidAssetManagerBase manager, AssetKey key) {
		if(key instanceof ResourceAssetKey k
				&& manager.isDeepValid(String.class, k.resourceName())) {
			final var name = manager.loadData(String.class, k.resourceName());
			return resources.getResource(name) != null;
		}
		return false;
	}
	
	@Override
	public Source<Resource> load(LoadContext context, AssetKey ckey) {
		if(ckey instanceof ResourceAssetKey key) {
			final var name = context.load(String.class, key.resourceName());
			return new ResourceAsset(resources, name);
		}
		return null;
	}
	
	private static final class ResourceAsset implements Source<Resource> {
		
		private final ResourceLocation resources;
		private static final long serialVersionUID = 1L;
		private final Source<? extends String> name;
		
		public ResourceAsset(ResourceLocation resources, Source<? extends String> name) {
			this.name = name;
			this.resources = resources;
		}
		
		@Override
		public int characteristics() {
			return ResourceAssetProvider.CHARACTERISTICS;
		}
		
		@Override
		public SourceProvider<Resource> openProvider() {
			return new ReduceProvider<>(new ResourceAssetProvider(resources, name));
		}
		
		public class ResourceAssetProvider implements SourceProvider<ResourceSource> {
			
			
			private final ResourceLocation resources;
			private static final int CHARACTERISTICS = 0;
			private final SourceProvider<? extends String> name;
			
			public ResourceAssetProvider(ResourceLocation resources,
					Source<? extends String> name) {
				this.name = name.openProvider();
				this.resources = resources;
			}
			
			@Override
			public int characteristics() {
				return CHARACTERISTICS;
			}
			
			@Override
			public void close() {
			}
			
			@Override
			public ResourceSource get() {
				final var name = this.name.get();
				final var resource = resources.getResource(name);
				if(resource == null)
					throw new IllegalArgumentException("resource not found " + name);
				return new ResourceSource(resource);
			}
			
			@Override
			public boolean isChenge() {
				return name.isChenge();
			}
			
		}
		
	}
	
	
}
