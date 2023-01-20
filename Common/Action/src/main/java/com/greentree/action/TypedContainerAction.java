package com.greentree.action;

import com.greentree.action.container.TypedListenerContainer;

/** @author Arseny Latyshev */
public abstract class TypedContainerAction<T, L, LC extends TypedListenerContainer<T, L>> {
	
	protected final LC listeners;
	
	public TypedContainerAction(LC container) {
		this.listeners = container;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MultiAction [");
		builder.append(listeners);
		builder.append("]");
		return builder.toString();
	}
	
	
	public ListenerCloser addListener(T t, L listener) {
		return listeners.add(t, listener);
	}
	
	public ListenerCloser addListener(L listener) {
		return listeners.add(listener);
	}
	
}
