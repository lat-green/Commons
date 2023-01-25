package com.greentree.commons.assets.value.source;

import java.io.Serializable;

public interface Source<T> extends Serializable, AutoCloseable {
	
	@Override
	default void close() {
	}
	
	T get();
	
	boolean isChenge();
	
	boolean isConst();
	
}
