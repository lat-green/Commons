package com.greentree.action.observer.run;

import com.greentree.action.MultiAction;

public class RunAction extends MultiAction<Runnable> implements IRunnableAction {
	private static final long serialVersionUID = 1L;

	@Override
	public void event() {
		for(var l : listeners)
			l.run();
	}

}
