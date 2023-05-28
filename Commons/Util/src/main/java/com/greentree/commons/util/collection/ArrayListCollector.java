package com.greentree.commons.util.collection;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;


public class ArrayListCollector<T> implements Collector<T, ArrayList<T>, ArrayList<T>> {
	
	public static final ArrayListCollector<?> INSTANCE = new ArrayListCollector<>();
	
	@SuppressWarnings("unchecked")
	public static <T> ArrayListCollector<T> INSTANCE() {
		return (ArrayListCollector<T>) INSTANCE;
	}
	
	
	@Override
	public BiConsumer<ArrayList<T>, T> accumulator() {
		return (t, u) -> t.add(u);
	}
	
	@Override
	public Set<Characteristics> characteristics() {
		return Set.of();
	}
	
	
	@Override
	public BinaryOperator<ArrayList<T>> combiner() {
		return (t, u) -> {
			t.addAll(u);
			return t;
		};
	}
	
	
	@Override
	public Function<ArrayList<T>, ArrayList<T>> finisher() {
		return Function.identity();
	}
	
	
	@Override
	public Supplier<ArrayList<T>> supplier() {
		return ArrayList::new;
	}
	
	
}
