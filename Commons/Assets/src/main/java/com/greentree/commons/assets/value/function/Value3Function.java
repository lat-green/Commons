package com.greentree.commons.assets.value.function;

import com.greentree.commons.assets.value.merge.Group3;

public interface Value3Function<T1, T2, T3, R>
		extends Value1Function<Group3<? extends T1, ? extends T2, ? extends T3>, R> {
	
	@Override
	default R apply(Group3<? extends T1, ? extends T2, ? extends T3> value) {
		return apply(value.t1(), value.t2(), value.t3());
	}
	
	R apply(T1 value1, T2 value2, T3 value3);
	
}
