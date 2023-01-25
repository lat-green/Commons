package com.greentree.commons.assets.value;

import com.greentree.commons.action.observable.ObjectObservable;

public abstract class AbstractConstMapValue<T, R> implements Value<R> {
	
	private static final long serialVersionUID = 1L;
	
	private final T source;
	
	private transient R result;
	
	@Override
	public boolean isConst() {
		return true;
	}
	
	@java.io.Serial
	private void readObject(java.io.ObjectInputStream s)
			throws java.io.IOException, ClassNotFoundException {
		s.defaultReadObject();
		init();
	}
	
	public AbstractConstMapValue(T source) {
		this.source = source;
		init();
	}
	
	private void init() {
		result = map(source);
	}
	
	protected abstract R map(T source);
	
	@Override
	public ObjectObservable<R> observer() {
		return ObjectObservable.getNull();
	}
	
	@Override
	public R get() {
		return result;
	}
	
}
