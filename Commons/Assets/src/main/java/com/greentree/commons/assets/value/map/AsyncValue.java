package com.greentree.commons.assets.value.map;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.assets.value.AbstractValue;


public final class AsyncValue<T, R> extends AbstractValue<R> implements MapValue<T, R> {
	
	private final MapValue<T, R> value;
	
	private Future<?> task;
	private final ExecutorService executor;
	
	public AsyncValue(ExecutorService executor, MapValue<T, R> value) {
		this.value = value;
		this.executor = executor;
	}
	
	@Override
	public R get() {
		await();
		return value.get();
	}
	
	@Override
	public boolean isSerializeKey() {
		return false;
	}
	
	@Override
	public ObjectObservable<R> observer() {
		return value.observer();
	}
	
	@Override
	public void set(T value) {
		synchronized(this) {
			await();
			task = executor.submit(()-> {
				this.value.set(value);
				task = null;
			});
		}
	}
	
	@Override
	public Serializable toSerializable() {
		await();
		return value.toSerializable();
	}
	
	@Override
	public String toString() {
		return "Async " + value;
	}
	
	private void await() {
		synchronized(this) {
			if(task != null)
				try {
					task.get();
				}catch(InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
		}
	}
	
}
