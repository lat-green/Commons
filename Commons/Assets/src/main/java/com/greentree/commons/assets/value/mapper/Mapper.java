package com.greentree.commons.assets.value.mapper;

import java.io.Serializable;

public interface Mapper<T, R> extends Serializable, AutoCloseable {
	
	@Override
	void close();
	
	void set(T value);
	R get();
	
	Mapper<T, R> copy();
	
}
