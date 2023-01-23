package com.greentree.commons.assets.value;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.assets.value.map.MapValue;

public final class MutableWrapedValue<T, R> extends AbstractValue<R> implements SerializableValue<R>

{
	
	private static final long serialVersionUID = 1L;
	
	private final MapValue<T, R> result;
	private Value<? extends T> source;
	private transient ListenerCloser lc;
	
	public MutableWrapedValue(Value<? extends T> value, MapValue<T, R> result) {
		this.result = result;
		set0(value);
	}
	
	@Override
	public void close() {
		if(lc != null) {
			lc.close();
			lc = null;
		}
		source.close();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null || getClass() != obj.getClass())
			return false;
		MutableWrapedValue<?, ?> other = (MutableWrapedValue<?, ?>) obj;
		return Objects.equals(result, other.result);
	}
	
	@Override
	public R get() {
		trim();
		return result.get();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(result);
	}
	
	@Override
	public boolean isConst() {
		return false;
	}
	
	@Override
	public ObjectObservable<R> observer() {
		return result.observer();
	}
	
	public void set(Value<? extends T> value) {
		close();
		set0(value);
	}
	
	@Override
	public String toString() {
		return "MutableWrapedValue [" + source + ", " + result + "]";
	}
	
	@java.io.Serial
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		set0(source);
	}
	
	private void set0(Value<? extends T> value) {
		Objects.requireNonNull(value);
		this.source = value;
		lc = Values.set(value, result);
	}
	
	private void trim() {
		source.get();
	}
	
}
