package com.greentree.commons.assets.serializator.context;

import java.util.function.Function;

import com.greentree.commons.assets.serializator.manager.AssetManagerBase;
import com.greentree.commons.assets.serializator.manager.ParallelAssetManger;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.Values;
import com.greentree.commons.assets.value.merge.Group2;
import com.greentree.commons.assets.value.merge.Group3;
import com.greentree.commons.assets.value.merge.Group4;
import com.greentree.commons.assets.value.merge.Group5;
import com.greentree.commons.assets.value.merge.Group6;
import com.greentree.commons.util.iterator.IteratorUtil;

public interface LoadContext extends DefaultLoadContext, AssetManagerBase {
	
	default <T, R> Value<R> map(Value<T> source, Function<? super T, ? extends R> mapFunction) {
		return Values.map(source, mapFunction);
	}
	
	default <T1, T2, R> Value<R> map(Value<T1> source1, Value<T2> source2,
			Function<? super Group2<? extends T1, ? extends T2>, ? extends R> mapFunction) {
		final var m = Values.merge(source1, source2);
		return map(m, mapFunction);
	}
	
	default <T1, T2, T3, R> Value<R> map(Value<T1> source1, Value<T2> source2, Value<T3> source3,
			Function<? super Group3<? extends T1, ? extends T2, ? extends T3>, ? extends R> mapFunction) {
		final var m = Values.merge(source1, source2, source3);
		return map(m, mapFunction);
	}
	
	default <T1, T2, T3, T4, R> Value<R> map(Value<T1> source1, Value<T2> source2,
			Value<T3> source3, Value<T4> source4,
			Function<? super Group4<? extends T1, ? extends T2, ? extends T3, ? extends T4>, ? extends R> mapFunction) {
		final var m = Values.merge(source1, source2, source3, source4);
		return map(m, mapFunction);
	}
	
	default <T1, T2, T3, T4, T5, R> Value<R> map(Value<T1> source1, Value<T2> source2,
			Value<T3> source3, Value<T4> source4, Value<T5> source5,
			Function<? super Group5<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5>, ? extends R> mapFunction) {
		final var m = Values.merge(source1, source2, source3, source4, source5);
		return map(m, mapFunction);
	}
	
	default <T1, T2, T3, T4, T5, T6, R> Value<R> map(Value<T1> source1, Value<T2> source2,
			Value<T3> source3, Value<T4> source4, Value<T5> source5, Value<T6> source6,
			Function<? super Group6<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6>, ? extends R> mapFunction) {
		final var m = Values.merge(source1, source2, source3, source4, source5, source6);
		return map(m, mapFunction);
	}
	
	default <T> Value<? extends Iterable<T>> merge(Iterable<? extends Value<? extends T>> sources) {
		return Values.merge(sources);
	}
	
	@SuppressWarnings("unchecked")
	default <T> Value<? extends Iterable<T>> merge(Value<? extends T>... sources) {
		return merge(IteratorUtil.iterable(sources));
	}
	
	ParallelAssetManger parallel();
	
}
