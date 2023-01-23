package com.greentree.commons.assets.source.merge;

import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.provider.SourceProvider;
import com.greentree.commons.assets.value.merge.Group5;

public final class M5Source<T1, T2, T3, T4, T5> implements Source<Group5<T1, T2, T3, T4, T5>> {
	
	private static final long serialVersionUID = 1L;
	
	private final Source<T1> source1;
	private final Source<T2> source2;
	private final Source<T3> source3;
	private final Source<T4> source4;
	private final Source<T5> source5;
	
	public M5Source(Source<T1> source1, Source<T2> source2, Source<T3> source3, Source<T4> source4,
			Source<T5> source5) {
		this.source1 = source1;
		this.source2 = source2;
		this.source3 = source3;
		this.source4 = source4;
		this.source5 = source5;
	}
	
	@Override
	public int characteristics() {
		return M2Provider.CHARACTERISTICS;
	}
	
	@Override
	public SourceProvider<Group5<T1, T2, T3, T4, T5>> openProvider() {
		return new M5Provider<>(source1, source2, source3, source4, source5);
	}
	
}
