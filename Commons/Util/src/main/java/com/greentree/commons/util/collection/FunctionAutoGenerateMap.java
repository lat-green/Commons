package com.greentree.commons.util.collection;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import com.greentree.commons.util.function.CheckedFunction;
import com.greentree.commons.util.function.CheckedSupplier;

public class FunctionAutoGenerateMap<K, V> extends AutoGenerateMap<K, V> {
	
	private static final long serialVersionUID = 1L;
	
	private final Function<K, V> function;
	
	public FunctionAutoGenerateMap(Function<K, V> function) {
		this.function = function;
	}
	
	public FunctionAutoGenerateMap(Function<K, V> function, int initialCapacity) {
		super(initialCapacity);
		this.function = function;
	}
	
	public FunctionAutoGenerateMap(Function<K, V> function, int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		this.function = function;
	}
	
	public FunctionAutoGenerateMap(Function<K, V> function, Map<? extends K, ? extends V> m) {
		super(m);
		this.function = function;
	}
	
	public FunctionAutoGenerateMap(Supplier<V> supplier) {
		this.function = t->supplier.get();
	}
	
	public static <K, V> FunctionAutoGenerateMap<K, V> build(CheckedFunction<K, V> function) {
		return new FunctionAutoGenerateMap<>(function.toNonCheked());
	}
	
	public static <K, V> FunctionAutoGenerateMap<K, V> build(CheckedSupplier<V> supplier) {
		return new FunctionAutoGenerateMap<>(supplier);
	}
	
	@Override
	protected V generate(K k) {
		return function.apply(k);
	}
	
	@Override
	protected void generateNew(V v) {
		
	}
	
}
