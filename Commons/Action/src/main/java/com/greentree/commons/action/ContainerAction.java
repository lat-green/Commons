package com.greentree.commons.action;

import com.greentree.commons.action.container.ListenerContainer;

/** @author Arseny Latyshev */
public abstract class ContainerAction<L, LC extends ListenerContainer<L>> {
	
	protected final LC listeners;
	
	public ContainerAction(LC container) {
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
	
	public ListenerCloser addListener(L listener) {
		return listeners.add(listener);
	}
	
	public int listenerSize() {
		return listeners.size();
	}
	
	public void clear() {
		listeners.clear();
	}
	
}
