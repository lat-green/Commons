package com.greentree.common.util.function;

import java.util.Objects;
import java.util.function.Function;

import com.greentree.common.util.exception.WrappedException;

@FunctionalInterface
public interface CheckedFunction<T, R> {
	
	R apply(T t) throws Exception;
	
	default Function<T, R> toNonCheked() {
		return t-> {
			try {
				return apply(t);
			}catch(Exception e) {
				throw new WrappedException(e);
			}
		};
	}
	
	default <V> CheckedFunction<V, R> compose(CheckedFunction<? super V, ? extends T> before) {
		Objects.requireNonNull(before);
		return (V v)->apply(before.apply(v));
	}
	
	default <V> CheckedFunction<T, V> andThen(CheckedFunction<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (T t)->after.apply(apply(t));
	}
	
	default <V> CheckedFunction<V, R> compose(Function<? super V, ? extends T> before) {
		Objects.requireNonNull(before);
		return (V v)->apply(before.apply(v));
	}
	
	default <V> CheckedFunction<T, V> andThen(Function<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (T t)->after.apply(apply(t));
	}
	
	static <T> CheckedFunction<T, T> identity() {
		return t->t;
	}
}
