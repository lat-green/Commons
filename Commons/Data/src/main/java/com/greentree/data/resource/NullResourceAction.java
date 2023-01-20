package com.greentree.data.resource;

import com.greentree.action.observable.RunObservable;

public final class NullResourceAction implements ResourceAction {
	
	private static final long serialVersionUID = 1L;
	
	
	public static final NullResourceAction NULL_RESOURCE_ACTION = new NullResourceAction();
	
	private NullResourceAction() {
	}
	
	@Override
	public RunObservable getOnCreate() {
		return RunObservable.NULL;
	}
	
	@Override
	public RunObservable getOnDelete() {
		return RunObservable.NULL;
	}
	
	@Override
	public RunObservable getOnModify() {
		return RunObservable.NULL;
	}
	
}
