package com.greentree.commons.util.function;

import java.util.function.Function;
import java.util.function.Supplier;

public final class LambdaSoftSaveFunction<T, R> extends SoftSaveFunction<T, R> {
	
	private static final long serialVersionUID = 1L;
	
	private final Function<? super T, ? extends R> lambda;
	
	public LambdaSoftSaveFunction(Function<? super T, ? extends R> lambda) {
		this.lambda = lambda;
	}
	
	public LambdaSoftSaveFunction(Supplier<? extends R> lambda) {
		this.lambda = x->lambda.get();
	}
	
	@Override
	protected R createData(T t) {
		return lambda.apply(t);
	}
	
}
