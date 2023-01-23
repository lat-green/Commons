package com.greentree.commons.assets.source.merge;

import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.provider.SourceProvider;
import com.greentree.commons.assets.value.merge.Group2;


public final class M2Provider<T1, T2> implements SourceProvider<Group2<T1, T2>> {
	
	public static final int CHARACTERISTICS = NOT_NULL;
	private final SourceProvider<T1> provider1;
	private final SourceProvider<T2> provider2;
	
	public M2Provider(Source<T1> source1, Source<T2> source2) {
		this.provider1 = source1.openProvider();
		this.provider2 = source2.openProvider();
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public Group2<T1, T2> get() {
		final var v1 = provider1.get();
		final var v2 = provider2.get();
		return new Group2<>(v1, v2);
	}
	
	@Override
	public boolean isChenge() {
		return provider1.isChenge() || provider2.isChenge();
	}
	
	@Override
	public String toString() {
		return "M2Provider [" + provider1 + ", " + provider2 + "]";
	}
	
}
