package com.greentree.commons.assets.source.merge;

import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.provider.SourceProvider;
import com.greentree.commons.assets.value.merge.Group4;

public final class M4Source<T1, T2, T3, T4> implements Source<Group4<T1, T2, T3, T4>> {
	
	private static final long serialVersionUID = 1L;
	
	private final Source<T1> source1;
	private final Source<T2> source2;
	private final Source<T3> source3;
	private final Source<T4> source4;
	
	public M4Source(Source<T1> source1, Source<T2> source2, Source<T3> source3,
			Source<T4> source4) {
		this.source1 = source1;
		this.source2 = source2;
		this.source3 = source3;
		this.source4 = source4;
	}
	
	@Override
	public int characteristics() {
		return M2Provider.CHARACTERISTICS;
	}
	
	@Override
	public SourceProvider<Group4<T1, T2, T3, T4>> openProvider() {
		return new M4Provider<>(source1, source2, source3, source4);
	}
	
}
