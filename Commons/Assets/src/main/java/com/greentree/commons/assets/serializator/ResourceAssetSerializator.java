package com.greentree.commons.assets.serializator;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.ResourceAssetKey;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.serializator.manager.DeepValidAssetManagerBase;
import com.greentree.commons.assets.value.ConstWrappedValue;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.map.MapValueImpl;
import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.data.resource.location.ResourceLocation;


public class ResourceAssetSerializator implements AssetSerializator<Resource> {
	
	private final ResourceLocation resources;
	
	public ResourceAssetSerializator(ResourceLocation resources) {
		this.resources = resources;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(key instanceof ResourceAssetKey k) {
			return manager.canLoad(String.class, k.resourceName());
		}
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
	public Value<Resource> load(LoadContext context, AssetKey ckey) {
		if(ckey instanceof ResourceAssetKey key) {
			final var name = context.load(String.class).set(key.resourceName()).load();
			return ConstWrappedValue.newValue(name, new ResourceAsset());
		}
		return null;
	}
	
	private final class ResourceAsset extends MapValueImpl<String, Resource> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public String toString() {
			return "Resource [" + source() + "]";
		}
		
		private transient ListenerCloser lc;
		
		@Override
		public void close() {
			if(lc != null)
				lc.close();
			super.close();
		}
		
		@Override
		public Resource map(String name) {
			final var res = resources.getResource(name);
			if(res == null)
				throw new IllegalArgumentException("resource not found " + name);
			
			if(lc != null)
				lc.close();
			
			lc = res.getAction().getOnModify().addListener(()-> {
				result_event();
			});
			
			return res;
		}
		
		@Override
		protected Resource map(String name, Resource dest) {
			return map(name);
		}
		
	}
	
	
}
