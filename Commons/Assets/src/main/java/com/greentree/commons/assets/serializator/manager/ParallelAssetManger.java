package com.greentree.commons.assets.serializator.manager;


public interface ParallelAssetManger extends ValueAssetManager, AutoCloseable {

	@Override
	void close();
	
}
