package com.greentree.commons.assets.source.provider;

import java.util.function.Consumer;

import com.greentree.commons.assets.source.SourceCharacteristics;

public interface SourceProvider<T> extends SourceCharacteristics<T>, AutoCloseable {
	
	default T getOld() {
		return get();
	}
	
	@Override
	void close();
	
	boolean isChenge();
	
	default boolean tryGet(Consumer<? super T> action) {
		if(!isChenge())
			return false;
		action.accept(get());
		return true;
	}
	
}
