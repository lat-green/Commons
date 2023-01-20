package com.greentree.data.resource;

import java.io.Serializable;

import com.greentree.action.observable.RunObservable;

public interface ResourceAction extends Serializable {
	
	RunObservable getOnCreate();
	RunObservable getOnDelete();
	RunObservable getOnModify();
	
}
