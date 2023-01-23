package com.greentree.commons.assets.source.merge;

import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.provider.SourceProvider;
import com.greentree.commons.assets.value.merge.Group3;

public final class M3Source<T1, T2, T3> implements Source<Group3<T1, T2, T3>> {
	
	private static final long serialVersionUID = 1L;
	
	private final Source<T1> source1;
	private final Source<T2> source2;
	private final Source<T3> source3;
	
	public M3Source(Source<T1> source1, Source<T2> source2, Source<T3> source3) {
		this.source1 = source1;
		this.source2 = source2;
		this.source3 = source3;
	}
	
	@Override
	public int characteristics() {
		return M2Provider.CHARACTERISTICS;
	}
	
	@Override
	public SourceProvider<Group3<T1, T2, T3>> openProvider() {
		return new M3Provider<>(source1, source2, source3);
	}
	
}
