package com.greentree.commons.assets.value;

import java.io.Serializable;
import java.util.ArrayList;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observable.ObjectObservable;

public final class IteratorValue<T> extends AbstractValue<Iterable<? extends Value<? extends T>>>
		implements SerializableValue<Iterable<? extends Value<? extends T>>> {
	
	private static final long serialVersionUID = 1L;
	
	private transient ListenerCloser lc;
	
	private final MutableValue<Iterable<? extends Value<? extends T>>> result = new MutableValue<>();
	
	@Override
	public Iterable<? extends Value<? extends T>> get() {
		return result.get();
	}
	
	@Override
	public ObjectObservable<Iterable<? extends Value<? extends T>>> observer() {
		return result.observer();
	}
	
	public void set(Iterable<? extends Value<? extends T>> value) {
		if(value != null) {
			if(lc != null)
				lc.close();
			final var lcs = new ArrayList<ListenerCloser>();
			for(var v : value)
				if(v != null) {
					final var lc = v.observer().addListener(new ElementListener());
					lcs.add(lc);
				}
			lc = ListenerCloser.merge(lcs);
			result.set(value);
		}
	}
	
	@Override
	public void close() {
		lc.close();
		result.close();
	}
	
	@Override
	public String toString() {
		return "IteratorValue [" + get() + "]";
	}
	
	private void event() {
		result.event();
	}
	
	private final class ElementListener implements Runnable, Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void run() {
			event();
		}
		
	}
	
}
