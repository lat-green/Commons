package com.greentree.commons.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public final class LambdaSaveBiFunction<T1, T2, R> extends SaveBiFunction<T1, T2, R> {
	
	private static final long serialVersionUID = 1L;
	
	private final BiFunction<? super T1, ? super T2, ? extends R> lambda;
	
	public LambdaSaveBiFunction(BiFunction<? super T1, ? super T2, ? extends R> lambda) {
		this.lambda = lambda;
	}
	
	public LambdaSaveBiFunction(Function<? super T1, ? extends R> lambda) {
		this.lambda = (x, y)->lambda.apply(x);
	}
	
	public LambdaSaveBiFunction(Supplier<? extends R> lambda) {
		this.lambda = (x, y)->lambda.get();
	}
	
	
	@Override
	protected R createPair(T1 t1, T2 t2) {
		return lambda.apply(t1, t2);
	}
}
