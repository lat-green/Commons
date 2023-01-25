package com.greentree.commons.assets.serializator.context;

import com.greentree.commons.assets.serializator.manager.AssetManagerBase;
import com.greentree.commons.assets.serializator.manager.ParallelAssetManger;
import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.Sources;
import com.greentree.commons.assets.source.function.Source1Function;
import com.greentree.commons.assets.source.merge.Group2;
import com.greentree.commons.assets.source.merge.Group3;
import com.greentree.commons.assets.source.merge.Group4;
import com.greentree.commons.assets.source.merge.Group5;
import com.greentree.commons.assets.source.merge.Group6;
import com.greentree.commons.util.iterator.IteratorUtil;

public interface LoadContext extends DefaultLoadContext, AssetManagerBase {
	
	default <T, R> Source<R> map(Source<T> source, Source1Function<? super T, R> mapFunction) {
		return Sources.map(source, mapFunction);
	}
	
	default <T1, T2, R> Source<R> map(Source<T1> source1, Source<T2> source2,
			Source1Function<? super Group2<? extends T1, ? extends T2>, R> mapFunction) {
		final var m = Sources.merge(source1, source2);
		return map(m, mapFunction);
	}
	
	default <T1, T2, T3, R> Source<R> map(Source<T1> source1, Source<T2> source2,
			Source<T3> source3,
			Source1Function<? super Group3<? extends T1, ? extends T2, ? extends T3>, R> mapFunction) {
		final var m = Sources.merge(source1, source2, source3);
		return map(m, mapFunction);
	}
	
	default <T1, T2, T3, T4, R> Source<R> map(Source<T1> source1, Source<T2> source2,
			Source<T3> source3, Source<T4> source4,
			Source1Function<? super Group4<? extends T1, ? extends T2, ? extends T3, ? extends T4>, R> mapFunction) {
		final var m = Sources.merge(source1, source2, source3, source4);
		return map(m, mapFunction);
	}
	
	default <T1, T2, T3, T4, T5, R> Source<R> map(Source<T1> source1, Source<T2> source2,
			Source<T3> source3, Source<T4> source4, Source<T5> source5,
			Source1Function<? super Group5<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5>, R> mapFunction) {
		final var m = Sources.merge(source1, source2, source3, source4, source5);
		return map(m, mapFunction);
	}
	
	default <T1, T2, T3, T4, T5, T6, R> Source<R> map(Source<T1> source1, Source<T2> source2,
			Source<T3> source3, Source<T4> source4, Source<T5> source5, Source<T6> source6,
			Source1Function<? super Group6<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6>, R> mapFunction) {
		final var m = Sources.merge(source1, source2, source3, source4, source5, source6);
		return map(m, mapFunction);
	}
	
	default <T> Source<? extends Iterable<T>> merge(
			Iterable<? extends Source<? extends T>> sources) {
		return Sources.merge(sources);
	}
	
	@SuppressWarnings("unchecked")
	default <T> Source<? extends Iterable<T>> merge(Source<? extends T>... sources) {
		return merge(IteratorUtil.iterable(sources));
	}
	
	ParallelAssetManger parallel();
	
}
