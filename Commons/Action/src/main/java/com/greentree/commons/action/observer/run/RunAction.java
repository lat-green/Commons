package com.greentree.commons.action.observer.run;

import com.greentree.commons.action.MultiAction;

public class RunAction extends MultiAction<Runnable> implements IRunnableAction {
	private static final long serialVersionUID = 1L;

	@Override
	public void event() {
		for(var l : listeners)
			l.run();
	}

}
