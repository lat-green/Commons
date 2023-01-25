package com.greentree.commons.assets.source;

import com.greentree.commons.assets.source.function.Source1Function;
import com.greentree.commons.assets.source.merge.Group2;
import com.greentree.commons.assets.source.merge.Group3;
import com.greentree.commons.assets.source.merge.Group4;
import com.greentree.commons.assets.source.merge.Group5;
import com.greentree.commons.assets.source.merge.Group6;
import com.greentree.commons.assets.source.merge.M2Source;
import com.greentree.commons.assets.source.merge.M3Source;
import com.greentree.commons.assets.source.merge.M4Source;
import com.greentree.commons.assets.source.merge.M5Source;
import com.greentree.commons.assets.source.merge.M6Source;
import com.greentree.commons.assets.source.merge.MISource;

public class Sources {
	
	private Sources() {
	}
	
	public static <IN, OUT> Source<OUT> map(Source<IN> source,
			Source1Function<? super IN, OUT> mapFunction) {
		return new SourceFunctionMapSource<>(source, mapFunction);
	}
	
	public static <T1, T2> Source<Group2<T1, T2>> merge(Source<T1> source1, Source<T2> source2) {
		return new M2Source<>(source1, source2);
	}
	
	public static <T1, T2, T3> Source<Group3<T1, T2, T3>> merge(Source<T1> source1,
			Source<T2> source2, Source<T3> source3) {
		return new M3Source<>(source1, source2, source3);
	}
	
	public static <T1, T2, T3, T4> Source<Group4<T1, T2, T3, T4>> merge(Source<T1> source1,
			Source<T2> source2, Source<T3> source3, Source<T4> source4) {
		return new M4Source<>(source1, source2, source3, source4);
	}
	
	public static <T1, T2, T3, T4, T5> Source<Group5<T1, T2, T3, T4, T5>> merge(Source<T1> source1,
			Source<T2> source2, Source<T3> source3, Source<T4> source4, Source<T5> source5) {
		return new M5Source<>(source1, source2, source3, source4, source5);
	}
	
	public static <T1, T2, T3, T4, T5, T6> Source<Group6<T1, T2, T3, T4, T5, T6>> merge(
			Source<T1> source1, Source<T2> source2, Source<T3> source3, Source<T4> source4,
			Source<T5> source5, Source<T6> source6) {
		return new M6Source<>(source1, source2, source3, source4, source5, source6);
	}
	
	public static <T> Source<Iterable<T>> merge(Iterable<? extends Source<? extends T>> sources) {
		return new MISource<>(sources);
	}
	
	
	
}
