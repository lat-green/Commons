package com.greentree.commons.property;

import java.util.function.Consumer;
import java.util.function.LongUnaryOperator;

import com.greentree.commons.action.ListenerCloser;


final class MapLongProperty implements LongProperty {
	
	private static final int CHARACTERISTICS = 0;
	
	private final LongProperty source;
	private final LongUnaryOperator mapFunction;
	private final StoreLongProperty result;
	
	private MapLongProperty(LongProperty source, LongUnaryOperator mapFunction) {
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
	
	public static LongProperty create(LongProperty source,
			LongUnaryOperator mapFunction) {
		return new MapLongProperty(source, mapFunction);
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
