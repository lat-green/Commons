package com.greentree.commons.assets.value;

import java.util.ArrayList;
import java.util.List;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.util.iterator.IteratorUtil;

public final class DefaultValue<T> extends AbstractValue<T> implements SerializableValue<T> {
	
	private static final long serialVersionUID = 1L;
	private final List<? extends Value<? extends T>> values;
	private final MutableValue<T> result = new MutableValue<>();
	
	private transient ListenerCloser lcs;
	
	private DefaultValue(Iterable<? extends Value<? extends T>> values) {
		this.values = toList(values);
		init();
	}
	
	@SafeVarargs
	public static <T> Value<T> newValue(Value<T>... values) {
		final var iter = IteratorUtil.iterable(values);
		return newValue(iter);
	}
	
	public static <T> Value<T> newValue(Iterable<? extends Value<T>> values) {
		values = IteratorUtil.clone(IteratorUtil.filter(values, x->!(x.isConst() && x.isNull())));
		if(IteratorUtil.isEmpty(values))
			throw new IllegalArgumentException();
		if(IteratorUtil.size(values) == 1)
			return values.iterator().next();
		for(var v : values) {
			if(!v.isConst())
				break;
			if(!v.isNull())
				return v;
		}
		return new DefaultValue<>(values);
	}
	
	private static <T> List<T> toList(Iterable<? extends T> values) {
		final var list = new ArrayList<T>();
		for(var v : values)
			list.add(v);
		return list;
	}
	
	@Override
	public T get() {
		trim();
		return result.get();
	}
	
	@Override
	public void close() {
		if(lcs != null) {
			lcs.close();
			lcs = null;
		}
	}
	
	private void init() {
		close();
		trim();
		
		final var lcs = new ArrayList<ListenerCloser>();
		
		final Runnable l = ()-> {
			init();
		};
		
		for(var v : values) {
			final var lc = v.observer().addListener(l);
			lcs.add(lc);
		}
		
		for(var v : values)
			if(!v.isNull()) {
				result.set(v.get());
				this.lcs = ListenerCloser.merge(lcs);
				return;
			}
		ListenerCloser.merge(lcs).close();
		result.set(null);
	}
	
	
	private void trim() {
		for(var v : values)
			v.get();
	}
	
	@Override
	public ObjectObservable<T> observer() {
		return result.observer();
	}
	
	
	
}
