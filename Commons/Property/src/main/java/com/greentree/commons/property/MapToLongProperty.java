package com.greentree.commons.property;

import java.util.function.Consumer;
import java.util.function.ToLongFunction;

import com.greentree.commons.action.ListenerCloser;


final class MapToLongProperty<T> implements LongProperty {
	
	private static final int CHARACTERISTICS = 0;
	
	private final Property<? extends T> source;
	private final ToLongFunction<? super T> mapFunction;
	private final StoreLongProperty result;
	
	private MapToLongProperty(Property<? extends T> source, ToLongFunction<? super T> mapFunction) {
		this.source = source;
		this.mapFunction = mapFunction;
		{
			var value = mapFunction.applyAsLong(source.get());
			result = new StoreLongProperty(value);
		}
		source.addListener(v -> {
			var value = mapFunction.applyAsLong(source.get());
			result.set(value);
		});
	}
	
	public static <T> LongProperty create(Property<? extends T> source,
			ToLongFunction<? super T> mapFunction) {
		return new MapToLongProperty<>(source, mapFunction);
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public long get() {
		return result.get();
	}
	
	@Override
	public ListenerCloser addListener(Consumer<? super Long> listener) {
		return result.addListener(listener);
	}
	
}
