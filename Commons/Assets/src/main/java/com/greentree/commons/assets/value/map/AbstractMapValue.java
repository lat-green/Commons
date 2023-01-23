package com.greentree.commons.assets.value.map;

import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.assets.value.MutableValue;

public abstract class AbstractMapValue<T, R> implements MapValue<T, R> {
	
	private static final long serialVersionUID = 1L;
	
	private final MutableValue<R> result = new MutableValue<>();
	
	@Override
	public final R get() {
		synchronized(this) {
			return result.get();
		}
	}
	
	@Override
	public void close() {
		result.close();
	}
	
	@Override
	public final ObjectObservable<R> observer() {
		return result.observer();
	}
	
	@Override
	public final void set(T source) {
		synchronized(this) {
			var value = result.get();
			try {
				value = map0(source, value);
			}catch(RuntimeException e) {
				e.printStackTrace();
				return;
			}
			result.set(value);
		}
	}
	
	@Override
	public String toString() {
		return "MapValue [" + result.get() + "]";
	}
	
	private R map0(T source, R oldValue) {
		if(oldValue == null)
			return map(source);
		else
			return map(source, oldValue);
	}
	
	protected abstract R map(T source);
	
	protected abstract R map(T source, R oldValue);
	
	protected final void result_event() {
		result.event();
	}
	
}
