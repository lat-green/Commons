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
		if(IteratorUtil.isEmpty(values))
			throw new IllegalArgumentException();
		if(IteratorUtil.size(values) == 1)
			return values.iterator().next();
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
	
	private void init() {
		if(lcs != null)
			lcs.close();
		trim();
		
		final var lcs = new ArrayList<ListenerCloser>();
		
		final Runnable l = ()-> {
			init();
		};
		
		var nullOffset = 0;
		Value<? extends T> value;
		do {
			value = values.get(nullOffset++);
			final ListenerCloser lc = value.observer().addListener(l);
			lcs.add(lc);
			if(!value.isNull()) {
				result.set(value.get());
				break;
			}
		}while(true);
		
		this.lcs = ListenerCloser.merge(lcs);
	}
	
	
	private void trim() {
		for(var v : values)
			v.get();
	}
	
	@Override
	public boolean isNull() {
		return result.isNull();
	}
	
	@Override
	public ObjectObservable<T> observer() {
		return result.observer();
	}
	
	
	
}
