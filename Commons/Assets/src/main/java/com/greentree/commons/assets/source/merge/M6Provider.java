package com.greentree.commons.assets.source.merge;

import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.provider.SourceProvider;


public final class M6Provider<T1, T2, T3, T4, T5, T6>
		implements SourceProvider<Group6<T1, T2, T3, T4, T5, T6>> {
	
	public static final int CHARACTERISTICS = NOT_NULL;
	private final SourceProvider<T1> provider1;
	private final SourceProvider<T2> provider2;
	private final SourceProvider<T3> provider3;
	private final SourceProvider<T4> provider4;
	private final SourceProvider<T5> provider5;
	private final SourceProvider<T6> provider6;
	
	public M6Provider(Source<T1> source1, Source<T2> source2, Source<T3> source3,
			Source<T4> source4, Source<T5> source5, Source<T6> source6) {
		this.provider1 = source1.openProvider();
		this.provider2 = source2.openProvider();
		this.provider3 = source3.openProvider();
		this.provider4 = source4.openProvider();
		this.provider5 = source5.openProvider();
		this.provider6 = source6.openProvider();
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public Group6<T1, T2, T3, T4, T5, T6> get() {
		final var v1 = provider1.get();
		final var v2 = provider2.get();
		final var v3 = provider3.get();
		final var v4 = provider4.get();
		final var v5 = provider5.get();
		final var v6 = provider6.get();
		return new Group6<>(v1, v2, v3, v4, v5, v6);
	}
	
	@Override
	public boolean isChenge() {
		return provider1.isChenge() || provider2.isChenge() || provider3.isChenge()
				|| provider4.isChenge() || provider5.isChenge() || provider6.isChenge();
	}
	
}
