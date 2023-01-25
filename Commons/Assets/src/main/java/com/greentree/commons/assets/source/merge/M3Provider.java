package com.greentree.commons.assets.source.merge;

import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.provider.SourceProvider;


public final class M3Provider<T1, T2, T3> implements SourceProvider<Group3<T1, T2, T3>> {
	
	public static final int CHARACTERISTICS = NOT_NULL;
	private final SourceProvider<T1> provider1;
	private final SourceProvider<T2> provider2;
	private final SourceProvider<T3> provider3;
	
	public M3Provider(Source<T1> source1, Source<T2> source2, Source<T3> source3) {
		this.provider1 = source1.openProvider();
		this.provider2 = source2.openProvider();
		this.provider3 = source3.openProvider();
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public void close() {
		provider1.close();
		provider2.close();
		provider3.close();
	}
	
	@Override
	public Group3<T1, T2, T3> get() {
		final var v1 = provider1.get();
		final var v2 = provider2.get();
		final var v3 = provider3.get();
		return new Group3<>(v1, v2, v3);
	}
	
	@Override
	public boolean isChenge() {
		return provider1.isChenge() || provider2.isChenge() || provider3.isChenge();
	}
	
}
