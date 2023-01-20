package com.greentree.action;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import com.greentree.action.observer.object.IObjectAction;


public class PoolAction<T, A extends IObjectAction<T>> implements IObjectAction<T> {
	private static final long serialVersionUID = 1L;

	private final Collection<T> pool = new CopyOnWriteArrayList<>();
	private final A action;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PoolAction [");
		builder.append(action);
		builder.append("]");
		return builder.toString();
	}

	public PoolAction(A action) {
		this.action = action;
	}

	@Override
	public void event(T t) {
		if(pool.contains(t)) return;
		pool.add(t);
		action.event(t);
	}

	@Override
	public ListenerCloser addListener(Consumer<? super T> l) {
		final var res = action.addListener(l);
		for(var v : pool)
			l.accept(v);
		return res;
	}

	public A getAction() {
		return action;
	}

    @Override
    public int listenerSize() {
    	return action.listenerSize();
    }
	
}
