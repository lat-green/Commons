package com.greentree.commons.assets.value.map;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.util.Objects;

import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.assets.value.AbstractValue;
import com.greentree.commons.assets.value.MutableValue;

public abstract class MapValueImpl<T, R> extends AbstractValue<R>
		implements SerializableMapValue<T, R> {
	
	private static final long serialVersionUID = 1L;
	
	private transient MutableValue<R> result = new MutableValue<>();
	private T source;
	
	@Override
	public boolean isSerializeKey() {
		return true;
	}
	
	@Override
	public final R get() {
		synchronized(this) {
			return result.get();
		}
	}
	
	@Override
	public boolean isNull() {
		return result.isNull();
	}
	
	@Override
	public final ObjectObservable<R> observer() {
		return result.observer();
	}
	
	@Override
	public final void set(T source) {
		Objects.requireNonNull(source);
		synchronized(this) {
			this.source = source;
			reset();
		}
	}
	
	@Override
	public String toString() {
		return "MapValue [" + source + ", " + result.get() + "]";
	}
	
	@Serial
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		synchronized(this) {
			s.defaultReadObject();
			reset();
		}
	}
	
	@Serial
	private void writeObject(ObjectOutputStream s) throws IOException {
		synchronized(this) {
			s.defaultWriteObject();
		}
	}
	
	protected abstract R map(T source);
	
	protected abstract R map(T source, R dest);
	
	protected final void reset() {
		final R newValue;
		final var oldValue = result.get();
		try {
			if(oldValue == null)
				newValue = map(source);
			else
				newValue = map(source, oldValue);
		}catch(RuntimeException e) {
			e.printStackTrace();
			return;
		}
		if(newValue != null)
			result.set(newValue);
	}
	
	protected final void result_event() {
		result.event();
	}
	
	protected final T source() {
		return source;
	}
	
}
