package com.greentree.commons.assets.value;

import java.util.ArrayList;
import java.util.List;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.util.iterator.IteratorUtil;

public final class DefaultValue<T> implements Value<T> {
	
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
		final var list = new ArrayList<Value<T>>();
		for(var v : values)
			if(!(v.isConst() && v.isNull()))
				list.add(v);
		if(list.isEmpty())
			return NullValue.instance();
		if(list.size() == 1)
			return list.iterator().next();
		for(var v : list) {
			if(!v.isConst())
				break;
			if(!v.isNull())
				return v;
		}
		return new DefaultValue<>(list);
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
			if(!v.isNull()) {
				result.set(v.get());
				this.lcs = ListenerCloser.merge(lcs);
				return;
			}
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
