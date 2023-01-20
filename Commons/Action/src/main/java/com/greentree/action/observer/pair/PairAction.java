package com.greentree.action.observer.pair;

import java.util.function.BiConsumer;

import com.greentree.action.MultiAction;


public class PairAction<T1, T2> extends MultiAction<BiConsumer<? super T1, ? super T2>> implements IPairAction<T1, T2> {
	private static final long serialVersionUID = 1L;

	@Override
	public void event(T1 t1, T2 t2) {
		for(var l : listeners)
			l.accept(t1, t2);
	}

}
