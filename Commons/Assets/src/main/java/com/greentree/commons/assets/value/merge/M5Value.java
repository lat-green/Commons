package com.greentree.commons.assets.value.merge;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.assets.value.AbstractValue;
import com.greentree.commons.assets.value.MutableValue;
import com.greentree.commons.assets.value.SerializableValue;
import com.greentree.commons.assets.value.Value;

public final class M5Value<T1, T2, T3, T4, T5> extends AbstractValue<Group5<T1, T2, T3, T4, T5>>
		implements SerializableValue<Group5<T1, T2, T3, T4, T5>> {
	
	private static final long serialVersionUID = 1L;
	
	private final Value<? extends T1> value1;
	private final Value<? extends T2> value2;
	private final Value<? extends T3> value3;
	private final Value<? extends T4> value4;
	private final Value<? extends T5> value5;
	
	private transient MutableValue<Group5<T1, T2, T3, T4, T5>> result;
	private transient ListenerCloser lcs;
	
	public M5Value(Value<? extends T1> value1, Value<? extends T2> value2,
			Value<? extends T3> value3, Value<? extends T4> value4, Value<? extends T5> value5) {
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
		this.value4 = value4;
		this.value5 = value5;
		init();
	}
	
	@Override
	public void close() {
		if(lcs != null)
			lcs.close();
		lcs = null;
		value1.close();
		value2.close();
		value3.close();
		value4.close();
	}
	
	@Override
	public Group5<T1, T2, T3, T4, T5> get() {
		trim();
		return result.get();
	}
	
	@Override
	public boolean isConst() {
		return value1.isConst() && value2.isConst() && value3.isConst() && value4.isConst()
				&& value5.isConst();
	}
	
	@Override
	public boolean isNull() {
		return false;
	}
	
	@Override
	public ObjectObservable<Group5<T1, T2, T3, T4, T5>> observer() {
		return result.observer();
	}
	
	@Override
	public String toString() {
		return "Merge [" + value1 + ", " + value2 + ", " + value3 + ", " + value4 + ", " + value5
				+ "]";
	}
	
	private void init() {
		final Runnable l = ()-> {
			final var t1 = value1.get();
			final var t2 = value2.get();
			final var t3 = value3.get();
			final var t4 = value4.get();
			final var t5 = value5.get();
			final var g = new Group5<>(t1, t2, t3, t4, t5);
			result.set(g);
		};
		result = new MutableValue<>();
		l.run();
		final var lc1 = value1.observer().addListener(l);
		final var lc2 = value2.observer().addListener(l);
		final var lc3 = value3.observer().addListener(l);
		final var lc4 = value4.observer().addListener(l);
		final var lc5 = value5.observer().addListener(l);
		lcs = ListenerCloser.merge(lc1, lc2, lc3, lc4, lc5);
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
		value3.get();
		value4.get();
		value5.get();
	}
	
}
