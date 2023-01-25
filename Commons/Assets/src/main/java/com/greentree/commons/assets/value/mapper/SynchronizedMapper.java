package com.greentree.commons.assets.value.mapper;

import java.util.concurrent.locks.StampedLock;

import com.greentree.commons.assets.value.MutableValue;

public final class SynchronizedMapper<T, R> implements Mapper<T, R> {
	
	private static final long serialVersionUID = 1L;
	
	private final StampedLock lock = new StampedLock();
	private final Mapper<T, R> source;
	
	private SynchronizedMapper(Mapper<T, R> source) {
		this.source = source;
	}
	
	@Override
	public SynchronizedMapper<T, R> copy() {
		return newValue(source);
	}
	
	@Override
	public void close() {
		source.close();
	}
	
	public static <T> SynchronizedMapper<T, T> newValue() {
		return new SynchronizedMapper<>(new MutableValue<>());
	}
	
	public static <T, R> SynchronizedMapper<T, R> newValue(Mapper<T, R> value) {
		return new SynchronizedMapper<>(value.copy());
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
	public void set(T value) {
		final var stamp = lock.writeLock();
		try {
			source.set(value);
		}finally {
			lock.unlockWrite(stamp);
		}
	}
	
}
