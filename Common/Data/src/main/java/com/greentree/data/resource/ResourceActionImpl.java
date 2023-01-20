package com.greentree.data.resource;

import com.greentree.action.observable.RunObservable;
import com.greentree.action.observer.run.RunAction;

public class ResourceActionImpl implements ResourceAction {
	
	private static final long serialVersionUID = 1L;
	
	private final RunAction create, madify, delete;
	
	{
		create = new RunAction();
		madify = new RunAction();
		delete = new RunAction();
	}
	
	public void eventCreate() {
		create.event();
	}
	
	public void eventDelete() {
		delete.event();
	}
	
	public void eventModify() {
		madify.event();
	}
	
	@Override
	public RunObservable getOnCreate() {
		return create;
	}
	
	@Override
	public RunObservable getOnDelete() {
		return delete;
	}
	
	@Override
	public RunObservable getOnModify() {
		return madify;
	}
	
	
}
