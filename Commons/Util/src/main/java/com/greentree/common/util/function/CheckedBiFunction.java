package com.greentree.common.util.function;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.greentree.common.util.exception.WrappedException;

@FunctionalInterface
public interface CheckedBiFunction<T1, T2, R> {
	
	R apply(T1 t1, T2 t2) throws Exception;
	
	default BiFunction<T1, T2, R> toNonCheked() {
		return (t1, t2)-> {
			try {
				return apply(t1, t2);
			}catch(Exception e) {
				throw new WrappedException(e);
			}
		};
	}
	
	default <V> CheckedBiFunction<T1, T2, V> andThen(Function<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (t1, t2)->after.apply(apply(t1, t2));
	}
	
}
