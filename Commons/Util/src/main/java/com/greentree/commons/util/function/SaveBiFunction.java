package com.greentree.commons.util.function;

import java.io.Serializable;
import java.util.function.BiFunction;

import com.greentree.commons.util.cortege.Pair;

public abstract class SaveBiFunction<T1, T2, R> extends AbstractSaveFunction<Pair<T1, T2>, R>
		implements BiFunction<T1, T2, R>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public R apply(T1 t, T2 u) {
		return applyRaw(new Pair<>(t, u));
	}
	
	@Override
	protected R create(Pair<T1, T2> t) {
		return createPair(t.first, t.seconde);
	}
	
	protected abstract R createPair(T1 first, T2 seconde);
	
}
