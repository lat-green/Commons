package com.greentree.commons.action.observer.object;

import com.greentree.commons.util.cortege.Pair;

@Deprecated
public class PairObjectAction<F, S> extends EventAction<Pair<F, S>> {
	private static final long serialVersionUID = 1L;

	public void event(F first, S seconde) {
		super.event(new Pair<>(first, seconde));
	}

}
