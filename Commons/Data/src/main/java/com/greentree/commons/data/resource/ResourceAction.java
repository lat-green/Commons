package com.greentree.commons.data.resource;

import java.io.Serializable;

import com.greentree.commons.action.observable.RunObservable;

public interface ResourceAction extends Serializable {
	
	RunObservable getOnCreate();
	RunObservable getOnDelete();
	RunObservable getOnModify();
	
}
