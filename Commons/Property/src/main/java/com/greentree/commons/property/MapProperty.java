package com.greentree.commons.property;

import java.util.function.Consumer;
import java.util.function.Function;

import com.greentree.commons.action.ListenerCloser;


final class MapProperty<T, R> implements Property<R> {
	
	private static final int CHARACTERISTICS = 0;
	
	private final Property<? extends T> source;
	private final Function<? super T, ? extends R> mapFunction;
	private final StoreProperty<R> result;
	
	private MapProperty(Property<? extends T> source, Function<? super T, ? extends R> mapFunction) {
		this.source = source;
		this.mapFunction = mapFunction;
		{
			var value = mapFunction.apply(source.get());
			result = new StoreProperty<>(value);
		}
		source.addListener(v -> {
			var value = mapFunction.apply(source.get());
			result.set(value);
		});
	}
	
	public static <T, R> Property<R> create(Property<? extends T> source,
			Function<? super T, ? extends R> mapFunction) {
		System.out.println(source.hasCharacteristics(CONST));
		return new MapProperty<>(source, mapFunction);
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public ListenerCloser addListener(Consumer<? super R> listener) {
		return result.addListener(listener);
	}
	
	@Override
	public R get() {
		return result.get();
	}
	
}
