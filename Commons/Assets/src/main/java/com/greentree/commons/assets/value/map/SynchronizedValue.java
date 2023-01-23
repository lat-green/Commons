package com.greentree.commons.assets.value.map;

import java.util.concurrent.locks.StampedLock;

import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.assets.value.MutableValue;

public final class SynchronizedValue<T, R> implements MapValue<T, R> {
	
	private static final long serialVersionUID = 1L;
	
	private final StampedLock lock = new StampedLock();
	private final MapValue<T, R> source;
	
	private SynchronizedValue(MapValue<T, R> source) {
		this.source = source;
	}
	
	public static <T> MapValue<T, T> newValue() {
		return new SynchronizedValue<>(new MutableValue<>());
	}
	
	public static <T, R> MapValue<T, R> newValue(MapValue<T, R> value) {
		if(value.isConst())
			return value;
		return new SynchronizedValue<>(value);
	}
	
	@Override
	public void close() {
		source.close();
	}
	
	@Override
	public boolean isConst() {
		return source.isConst();
	}
	
	@Override
	public R get() {
		var stamp = lock.tryOptimisticRead();
		var v = source.get();
		if(!lock.validate(stamp)) {
			stamp = lock.readLock();
			try {
				v = source.get();
			}finally {
				lock.unlockRead(stamp);
			}
		}
		return v;
	}
	
	@Override
	public ObjectObservable<R> observer() {
		return source.observer();
	}
	
	@Override
	public void set(T value) {
		final var stamp = lock.writeLock();
		try {
			source.set(value);
		}finally {
			lock.unlockWrite(stamp);
		}
	}
	
}
