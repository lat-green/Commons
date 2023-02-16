package com.greentree.commons.util.function;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.greentree.commons.util.exception.WrappedException;

@FunctionalInterface
public interface CheckedBiFunction<T1, T2, R> extends BiFunction<T1, T2, R> {
	
	@SuppressWarnings("unchecked")
	static <T1, T2, R> CheckedBiFunction<T1, T2, R> build(BiFunction<? super T1, ? super T2, ? extends R> function) {
		if(function instanceof BiFunction)
			return (CheckedBiFunction<T1, T2, R>) function;
		return (t1, t2)->function.apply(t1, t2);
	}
	
	@Override
	default <V> CheckedBiFunction<T1, T2, V> andThen(Function<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (t1, t2)->after.apply(checkedApply(t1, t2));
	}
	
	@Override
	default R apply(T1 t1, T2 t2) {
		try {
			return checkedApply(t1, t2);
		}catch(Exception e) {
			throw new WrappedException(e);
		}
	}
	
	R checkedApply(T1 t1, T2 t2) throws Exception;
	
	@Deprecated
	default BiFunction<T1, T2, R> toNonCheked() {
		return this;
	}
	
}
