package com.greentree.commons.assets.value.function;

import com.greentree.commons.assets.value.merge.Group6;

public interface Value6Function<T1, T2, T3, T4, T5, T6, R> extends
		Value1Function<Group6<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6>, R> {
	
	@Override
	default R apply(
			Group6<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6> value) {
		return apply(value.t1(), value.t2(), value.t3(), value.t4(), value.t5(), value.t6());
	}
	
	R apply(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6);
	
}
