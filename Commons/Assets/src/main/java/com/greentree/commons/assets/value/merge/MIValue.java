package com.greentree.commons.assets.value.merge;

import java.util.ArrayList;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.assets.value.AbstractValue;
import com.greentree.commons.assets.value.MutableValue;
import com.greentree.commons.assets.value.SerializableValue;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.util.iterator.IteratorUtil;

public final class MIValue<T> extends AbstractValue<Iterable<T>>
		implements SerializableValue<Iterable<T>> {
	
	private static final long serialVersionUID = 1L;
	
	private final Iterable<? extends Value<? extends T>> values;
	
	private transient MutableValue<Iterable<T>> result;
	private transient ListenerCloser lcs;
	
	public MIValue(Iterable<? extends Value<? extends T>> values) {
		this.values = IteratorUtil.clone(values);
		init();
	}
	
	@SafeVarargs
	public MIValue(Value<? extends T>... values) {
		this(IteratorUtil.iterable(values));
	}
	
	@Override
	public void close() {
		if(lcs != null)
			lcs.close();
		lcs = null;
	}
	
	@Override
	public Iterable<T> get() {
		trim();
		return result.get();
	}
	
	@Override
	public boolean isConst() {
		return IteratorUtil.all(values, v->v.isConst());
	}
	
	
	@Override
	public boolean isNull() {
		return false;
	}
	
	@Override
	public ObjectObservable<Iterable<T>> observer() {
		return result.observer();
	}
	
	@Override
	public String toString() {
		return "Merge " + IteratorUtil.toString(values);
	}
	
	private void init() {
		final Runnable l = ()-> {
			final var ts = new ArrayList<T>();
			for(var v : values)
				ts.add(v.get());
			result.set(ts);
		};
		result = new MutableValue<>();
		l.run();
		final var lcs = new ArrayList<ListenerCloser>();
		for(var v : values) {
			final var lc = v.observer().addListener(l);
			lcs.add(lc);
		}
		this.lcs = ListenerCloser.merge(lcs);
	}
	
	@java.io.Serial
	private void readObject(java.io.ObjectInputStream s)
			throws java.io.IOException, ClassNotFoundException {
		s.defaultReadObject();
		init();
	}
	
	private void trim() {
		for(var v : values)
			v.get();
	}
	
}
