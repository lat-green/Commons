package com.greentree.commons.assets.source;

import java.io.Serializable;

import com.greentree.commons.assets.source.provider.SourceProvider;

public interface Source<T> extends SourceCharacteristics<T>, Serializable {
	
	SourceProvider<T> openProvider();
	
	default T get() {
		try(final var provider = openProvider()) {
			return provider.get();
		}
	}
	
}
