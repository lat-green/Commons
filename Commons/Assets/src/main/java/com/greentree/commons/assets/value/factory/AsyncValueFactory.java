package com.greentree.commons.assets.value.factory;

import java.util.concurrent.ExecutorService;

import com.greentree.commons.assets.value.map.AsyncValue;
import com.greentree.commons.assets.value.map.MapValue;


public class AsyncValueFactory implements ValueFactory {
	
	private final ExecutorService executor;
	
	public AsyncValueFactory(ExecutorService executor) {
		super();
		this.executor = executor;
	}
	
	@Override
	public <T, R> MapValue<T, R> create(MapValue<T, R> value) {
		return new AsyncValue<>(executor, value);
	}
	
}
