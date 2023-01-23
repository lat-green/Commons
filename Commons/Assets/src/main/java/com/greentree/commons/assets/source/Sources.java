package com.greentree.commons.assets.source;

import com.greentree.commons.assets.source.function.Source1Function;
import com.greentree.commons.assets.source.merge.Group6;
import com.greentree.commons.assets.source.merge.M6Source;

public class Sources {
	
	private Sources() {
	}
	
	public static <T1, T2, T3, T4, T5, T6> Source<Group6<T1, T2, T3, T4, T5, T6>> merge(
			Source<T1> source1, Source<T2> source2, Source<T3> source3, Source<T4> source4,
			Source<T5> source5, Source<T6> source6) {
		return new M6Source<>(source1, source2, source3, source4, source5, source6);
	}
	
	public static <IN, OUT> Source<OUT> map(Source<IN> source,
			Source1Function<? super IN, OUT> mapFunction) {
		return null;
	}
	
	
	
}
