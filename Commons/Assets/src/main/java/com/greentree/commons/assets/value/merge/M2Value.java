package com.greentree.commons.assets.value.merge;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.assets.value.AbstractValue;
import com.greentree.commons.assets.value.MutableValue;
import com.greentree.commons.assets.value.SerializableValue;
import com.greentree.commons.assets.value.Value;

public final class M2Value<T1, T2> extends AbstractValue<Group2<T1, T2>>
		implements SerializableValue<Group2<T1, T2>> {
	
	private static final long serialVersionUID = 1L;
	
	private final Value<? extends T1> value1;
	private final Value<? extends T2> value2;
	private transient MutableValue<Group2<T1, T2>> result;
	private transient ListenerCloser lcs;
	
	public M2Value(Value<? extends T1> value1, Value<? extends T2> value2) {
		this.value1 = value1;
		this.value2 = value2;
		init();
	}
	
	@Override
	public void close() {
		if(lcs != null)
			lcs.close();
		lcs = null;
	}
	
	@Override
	public Group2<T1, T2> get() {
		trim();
		return result.get();
	}
	
	@Override
	public boolean isConst() {
		return value1.isConst() && value2.isConst();
	}
	
	@Override
	public boolean isNull() {
		return false;
	}
	
	@Override
	public ObjectObservable<Group2<T1, T2>> observer() {
		return result.observer();
	}
	
	@Override
	public String toString() {
		return "Merge [" + value1 + ", " + value2 + "]";
	}
	
	private void init() {
		final Runnable l = ()-> {
			final var t1 = value1.get();
			final var t2 = value2.get();
			final var g = new Group2<>(t1, t2);
			result.set(g);
		};
		result = new MutableValue<>();
		l.run();
		final var lc1 = value1.observer().addListener(l);
		final var lc2 = value2.observer().addListener(l);
		lcs = ListenerCloser.merge(lc1, lc2);
	}
	
	@java.io.Serial
	private void readObject(java.io.ObjectInputStream s)
			throws java.io.IOException, ClassNotFoundException {
		s.defaultReadObject();
		init();
	}
	
	private void trim() {
		value1.get();
		value2.get();
	}
	
}
