package com.greentree.commons.assets.source.merge;

import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.provider.SourceProvider;
import com.greentree.commons.assets.value.merge.Group4;


public final class M4Provider<T1, T2, T3, T4> implements SourceProvider<Group4<T1, T2, T3, T4>> {
	
	public static final int CHARACTERISTICS = NOT_NULL;
	private final SourceProvider<T1> provider1;
	private final SourceProvider<T2> provider2;
	private final SourceProvider<T3> provider3;
	private final SourceProvider<T4> provider4;
	
	public M4Provider(Source<T1> source1, Source<T2> source2, Source<T3> source3,
			Source<T4> source4) {
		this.provider1 = source1.openProvider();
		this.provider2 = source2.openProvider();
		this.provider3 = source3.openProvider();
		this.provider4 = source4.openProvider();
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public Group4<T1, T2, T3, T4> get() {
		final var v1 = provider1.get();
		final var v2 = provider2.get();
		final var v3 = provider3.get();
		final var v4 = provider4.get();
		return new Group4<>(v1, v2, v3, v4);
	}
	
	@Override
	public boolean isChenge() {
		return provider1.isChenge() || provider2.isChenge() || provider3.isChenge()
				|| provider4.isChenge();
	}
	
}
