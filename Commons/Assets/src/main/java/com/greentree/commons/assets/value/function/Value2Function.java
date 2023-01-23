package com.greentree.commons.assets.value.function;

import com.greentree.commons.assets.value.merge.Group2;

public interface Value2Function<T1, T2, R>
		extends Value1Function<Group2<? extends T1, ? extends T2>, R> {
	
	@Override
	default R apply(Group2<? extends T1, ? extends T2> value) {
		return apply(value.t1(), value.t2());
	}
	
	default R applyWithDest(Group2<? extends T1, ? extends T2> value, R dest) {
		return applyWithDest(value.t1(), value.t2(), dest);
	}
	
	R apply(T1 value1, T2 value2);
	
	default R applyWithDest(T1 value1, T2 value2, R dest) {
		return apply(value1, value2);
	}
	
}
