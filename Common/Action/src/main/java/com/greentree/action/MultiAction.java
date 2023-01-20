package com.greentree.action;

import com.greentree.action.container.MultiContainer;

public abstract class MultiAction<L> extends ContainerAction<L, MultiContainer<L>> {
	
	public MultiAction() {
		super(new MultiContainer<>());
	}
	
}
