package com.greentree.commons.assets.value;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.assets.value.map.MapValue;

public final class ConstWrappedValue<T, R> extends AbstractValue<R>
		implements SerializableValue<R> {
	
	private static final long serialVersionUID = 1L;
	
	private final MapValue<T, R> result;
	private final Value<? extends T> source;
	private transient ListenerCloser lc;
	
	private ConstWrappedValue(Value<? extends T> value, MapValue<T, R> result) {
		this.source = value;
		this.result = result;
		init();
	}
	
	public static <T, R> Value<R> newValue(Value<? extends T> value,
			MapValue<? super T, R> result) {
		if(value.isConst()) {
			result.set(value.get());
			return result;
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
		return source.isConst();
	}
	
	@Override
	public ObjectObservable<R> observer() {
		return result.observer();
	}
	
	@Override
	public String toString() {
		return "ConstWrappedValue [" + source + ", " + result + "]";
	}
	
	private void init() {
		lc = Values.set(source, result);
	}
	
	@java.io.Serial
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		init();
	}
	
	private void trim() {
		source.get();
	}
	
}
