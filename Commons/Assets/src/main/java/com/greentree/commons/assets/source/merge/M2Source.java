package com.greentree.commons.assets.source.merge;

import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.provider.SourceProvider;

public final class M2Source<T1, T2> implements Source<Group2<T1, T2>> {
	
	private static final long serialVersionUID = 1L;
	
	private final Source<T1> source1;
	private final Source<T2> source2;
	
	public M2Source(Source<T1> source1, Source<T2> source2) {
		this.source1 = source1;
		this.source2 = source2;
	}
	
	@Override
	public int characteristics() {
		return M2Provider.CHARACTERISTICS;
	}
	
	@Override
	public SourceProvider<Group2<T1, T2>> openProvider() {
		return new M2Provider<>(source1, source2);
	}
	
	@Override
	public String toString() {
		return "M2Source [" + source1 + ", " + source2 + "]";
	}
	
}
