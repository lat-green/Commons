package com.greentree.commons.assets.value;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.assets.value.map.MapValue;

public final class ConstWrappedValue<T, R> extends AbstractValue<R>
		implements SerializableValue<R> {
	
	private static final long serialVersionUID = 1L;
	
	private final MapValue<T, R> result;
	private final Value<? extends T> value;
	private transient ListenerCloser lc;
	
	private ConstWrappedValue(Value<? extends T> value, MapValue<T, R> result) {
		this.value = value;
		this.result = result;
		init();
	}
	
	public static <T, R> Value<R> newValue(Value<? extends T> value,
			MapValue<? super T, R> result) {
		if(value.isConst()) {
			result.set(value.get());
			return result.toConst();
		}
		return new ConstWrappedValue<>(value, result);
	}
	
	@Override
	public void close() {
		if(lc != null) {
			lc.close();
			lc = null;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null || getClass() != obj.getClass())
			return false;
		ConstWrappedValue<?, ?> other = (ConstWrappedValue<?, ?>) obj;
		return Objects.equals(result, other.result);
	}
	
	@Override
	public R get() {
		trim();
		return result.get();
	}
	
	@Override
	public int hashCode() {
		return result.hashCode();
	}
	
	@Override
	public boolean isConst() {
		return value.isConst();
	}
	
	@Override
	public ObjectObservable<R> observer() {
		return result.observer();
	}
	
	@Override
	public String toString() {
		return "ConstWrappedValue [" + value + ", " + result + "]";
	}
	
	private void init() {
		lc = Values.set(value, result);
	}
	
	@java.io.Serial
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		init();
	}
	
	private void trim() {
		value.get();
	}
	
	@Override
	public Serializable toSerializable() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
