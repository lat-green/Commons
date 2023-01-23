package com.greentree.commons.assets.source.function;

import com.greentree.commons.assets.source.merge.Group5;

public interface Source5Function<T1, T2, T3, T4, T5, R> extends
		Source1Function<Group5<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5>, R> {
	
	@Override
	default R apply(
			Group5<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5> value) {
		return apply(value.t1(), value.t2(), value.t3(), value.t4(), value.t5());
	}
	
	R apply(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5);
	
}
