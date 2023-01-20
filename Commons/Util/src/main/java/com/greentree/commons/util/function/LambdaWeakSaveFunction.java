package com.greentree.commons.util.function;

import java.util.function.Function;
import java.util.function.Supplier;

public final class LambdaWeakSaveFunction<T, R> extends WeakSaveFunction<T, R> {
	
	private static final long serialVersionUID = 1L;
	
	private final Function<? super T, ? extends R> lambda;
	
	public LambdaWeakSaveFunction(Function<? super T, ? extends R> lambda) {
		this.lambda = lambda;
	}
	
	public LambdaWeakSaveFunction(Supplier<? extends R> lambda) {
		this.lambda = x->lambda.get();
	}
	
	@Override
	protected R createData(T t) {
		return lambda.apply(t);
	}
	
}
