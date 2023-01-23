package com.greentree.commons.assets.value;

import java.util.Objects;
import java.util.function.Consumer;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.assets.value.function.Value1Function;
import com.greentree.commons.assets.value.map.MapValue;
import com.greentree.commons.assets.value.map.ValueFuncMapValue;
import com.greentree.commons.assets.value.merge.Group2;
import com.greentree.commons.assets.value.merge.Group3;
import com.greentree.commons.assets.value.merge.Group4;
import com.greentree.commons.assets.value.merge.Group5;
import com.greentree.commons.assets.value.merge.Group6;
import com.greentree.commons.assets.value.merge.M2Value;
import com.greentree.commons.assets.value.merge.M3Value;
import com.greentree.commons.assets.value.merge.M4Value;
import com.greentree.commons.assets.value.merge.M5Value;
import com.greentree.commons.assets.value.merge.M6Value;
import com.greentree.commons.assets.value.merge.MIValue;
import com.greentree.commons.util.iterator.IteratorUtil;

public class Values {
	
	public static <T> ListenerCloser getValue(Value<? extends T> value,
			Consumer<? super T> listener) {
		listener.accept(value.get());
		return value.observer().addListener(listener);
	}
	
	public static <T, R> Value<R> map(Value<? extends T> source,
			Value1Function<? super T, R> func) {
		return ConstWrappedValue.newValue(source, new ValueFuncMapValue<>(func));
	}
	
	public static <T1, T2, R> Value<R> map(Value<T1> source1, Value<T2> source2,
			Value1Function<? super Group2<? extends T1, ? extends T2>, R> mapFunction) {
		final var g = merge(source1, source2);
		return map(g, mapFunction);
	}
	
	public static <T1, T2, T3, R> Value<R> map(Value<T1> source1, Value<T2> source2,
			Value<T3> source3,
			Value1Function<? super Group3<? extends T1, ? extends T2, ? extends T3>, R> mapFunction) {
		final var g = merge(source1, source2, source3);
		return map(g, mapFunction);
	}
	
	public static <T1, T2, T3, T4, R> Value<R> map(Value<T1> source1, Value<T2> source2,
			Value<T3> source3, Value<T4> source4,
			Value1Function<? super Group4<? extends T1, ? extends T2, ? extends T3, ? extends T4>, R> mapFunction) {
		final var g = merge(source1, source2, source3, source4);
		return map(g, mapFunction);
	}
	
	public static <T1, T2, T3, T4, T5, R> Value<R> map(Value<T1> source1, Value<T2> source2,
			Value<T3> source3, Value<T4> source4, Value<T5> source5,
			Value1Function<? super Group5<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5>, R> mapFunction) {
		final var g = merge(source1, source2, source3, source4, source5);
		return map(g, mapFunction);
	}
	
	public static <T1, T2, T3, T4, T5, T6, R> Value<R> map(Value<T1> source1, Value<T2> source2,
			Value<T3> source3, Value<T4> source4, Value<T5> source5, Value<T6> source6,
			Value1Function<? super Group6<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6>, R> mapFunction) {
		final var g = merge(source1, source2, source3, source4, source5, source6);
		return map(g, mapFunction);
	}
	
	public static <T> Value<Iterable<T>> merge(Iterable<? extends Value<? extends T>> sources) {
		return new MIValue<>(sources);
	}
	
	@SafeVarargs
	public static <T> Value<Iterable<T>> merge(Value<? extends T>... sources) {
		return merge(IteratorUtil.iterable(sources));
	}
	
	public static <T1, T2> Value<Group2<T1, T2>> merge(Value<? extends T1> source1,
			Value<? extends T2> source2) {
		return new M2Value<>(source1, source2);
	}
	
	public static <T1, T2, T3> Value<Group3<T1, T2, T3>> merge(Value<? extends T1> source1,
			Value<? extends T2> source2, Value<? extends T3> source3) {
		return new M3Value<>(source1, source2, source3);
	}
	
	public static <T1, T2, T3, T4> Value<Group4<T1, T2, T3, T4>> merge(Value<? extends T1> source1,
			Value<? extends T2> source2, Value<? extends T3> source3, Value<? extends T4> source4) {
		return new M4Value<>(source1, source2, source3, source4);
	}
	
	public static <T1, T2, T3, T4, T5> Value<Group5<T1, T2, T3, T4, T5>> merge(
			Value<? extends T1> source1, Value<? extends T2> source2, Value<? extends T3> source3,
			Value<? extends T4> source4, Value<? extends T5> source5) {
		return new M5Value<>(source1, source2, source3, source4, source5);
	}
	
	public static <T1, T2, T3, T4, T5, T6> Value<Group6<T1, T2, T3, T4, T5, T6>> merge(
			Value<? extends T1> source1, Value<? extends T2> source2, Value<? extends T3> source3,
			Value<? extends T4> source4, Value<? extends T5> source5, Value<? extends T6> source6) {
		return new M6Value<>(source1, source2, source3, source4, source5, source6);
	}
	
	public static <T> ListenerCloser set(Value<? extends T> value, MapValue<? super T, ?> result) {
		Objects.requireNonNull(value);
		Objects.requireNonNull(result);
		result.set(value.get());
		if(value.isConst())
			return ListenerCloser.EMPTY;
		return value.observer().addListener(v-> {
			result.set(v);
		});
	}
	
}
