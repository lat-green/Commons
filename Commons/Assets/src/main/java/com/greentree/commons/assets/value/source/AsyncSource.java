package com.greentree.commons.assets.value.source;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class AsyncSource<T> implements Source<T> {
	
	private static final long serialVersionUID = 1L;
	
	private static final Executor EXECUTOR = Executors.newWorkStealingPool();
	private final MutableSource<T> value = new MutableSource<>();
	
	private final Source<T> source;
	private volatile boolean run;
	
	private AsyncSource(Source<T> source) {
		this.source = source;
	}
	
	public static <T> Source<T> create(Source<T> source) {
		if(source.isConst())
			return source;
		return new AsyncSource<>(source);
	}
	
	@Override
	public T get() {
		return value.get();
	}
	
	@Override
	public boolean isConst() {
		return source.isConst();
	}
	
	@Override
	public boolean isChenge() {
		if(!run && source.isChenge()) {
			run = true;
			EXECUTOR.execute(()-> {
				value.set(source.get());
				run = false;
			});
		}
		return value.isChenge();
	}
	
}
