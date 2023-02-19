package com.greentree.commons.util.function;

import java.util.Objects;
import java.util.function.Function;

import com.greentree.commons.util.exception.WrappedException;

@FunctionalInterface
public interface CheckedFunction<T, R> extends Function<T, R> {
	
	
	@SuppressWarnings("unchecked")
	static <T, R> CheckedFunction<T, R> build(Function<? super T, ? extends R> consumer) {
		if(consumer instanceof CheckedFunction)
			return (CheckedFunction<T, R>) consumer;
		return t->consumer.apply(t);
	}
	
	static <T> CheckedFunction<T, T> identity() {
		return t->t;
	}
	
	default <V> CheckedFunction<T, V> andThen(CheckedFunction<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (var t)->after.apply(apply(t));
	}
	
	@Override
	default <V> CheckedFunction<T, V> andThen(Function<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (var t)->after.apply(apply(t));
	}
	
	@Override
	default R apply(T t) {
		try {
			return checkedApply(t);
		}catch(Throwable e) {
			throw new WrappedException(e);
		}
	}
	
	R checkedApply(T t) throws Throwable;
	
	default <V> CheckedFunction<V, R> compose(CheckedFunction<? super V, ? extends T> before) {
		Objects.requireNonNull(before);
		return (var v)->apply(before.apply(v));
	}
	
	@Override
	default <V> CheckedFunction<V, R> compose(Function<? super V, ? extends T> before) {
		Objects.requireNonNull(before);
		return (var v)->apply(before.apply(v));
	}
	
	@Deprecated
	default Function<T, R> toNonCheked() {
		return this;
	}
}
