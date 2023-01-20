package com.greentree.common.util.function;

import java.util.function.Function;
import java.util.function.Supplier;

public final class LambdaSaveFunction<T, R> extends SaveFunction<T, R> {
	
	private static final long serialVersionUID = 1L;
	
	private final Function<? super T, ? extends R> lambda;
	
	public LambdaSaveFunction(Function<? super T, ? extends R> lambda) {
		this.lambda = lambda;
	}
	
	public LambdaSaveFunction(Supplier<? extends R> lambda) {
		this.lambda = x->lambda.get();
	}
	
	@Override
	protected R create(T t) {
		return lambda.apply(t);
	}
	
}
