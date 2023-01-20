package com.greentree.action;

import com.greentree.action.container.TypedMultiContainer;

public abstract class TypedMultiAction<T, L> extends TypedContainerAction<T, L, TypedMultiContainer<T, L>> {
	
	public TypedMultiAction() {
		super(new TypedMultiContainer<>());
	}
	
}
