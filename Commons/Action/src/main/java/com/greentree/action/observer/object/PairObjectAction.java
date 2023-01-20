package com.greentree.action.observer.object;

import com.greentree.common.util.cortege.Pair;

@Deprecated
public class PairObjectAction<F, S> extends EventAction<Pair<F, S>> {
	private static final long serialVersionUID = 1L;

	public void event(F first, S seconde) {
		super.event(new Pair<>(first, seconde));
	}

}
