package com.greentree.commons.assets.source.function;

import com.greentree.commons.assets.source.merge.Group4;

public interface Source4Function<T1, T2, T3, T4, R>
		extends Source1Function<Group4<? extends T1, ? extends T2, ? extends T3, ? extends T4>, R> {
	
	@Override
	default R apply(Group4<? extends T1, ? extends T2, ? extends T3, ? extends T4> value) {
		return apply(value.t1(), value.t2(), value.t3(), value.t4());
	}
	
	R apply(T1 value1, T2 value2, T3 value3, T4 value4);
	
}
