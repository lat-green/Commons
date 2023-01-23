package com.greentree.commons.assets.mapper;

import com.greentree.commons.assets.source.MutableSource;
import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.provider.SourceProvider;
import com.greentree.commons.assets.store.Store;

public final class LazySource<IN, OUT> implements Mapper<IN, OUT> {
	
	private volatile boolean nextFlag;
	
	private IN nextValue;
	
	@Override
	public void set(IN value) {
		synchronized(this) {
			nextFlag = true;
			nextValue = value;
		}
	}
	
	private final class LazyProvider implements SourceProvider<OUT> {
		
		public static final int CHARACTERISTICS = 0;
		private final SourceProvider<OUT> out;
		
		@Override
		public void close() {
			out.close();
		}
		
		public LazyProvider(Source<OUT> out) {
			this.out = out.openProvider();
		}
		
		@Override
		public int characteristics() {
			return CHARACTERISTICS;
		}
		
		@Override
		public boolean isChenge() {
			return nextFlag;
		}
		
		@Override
		public OUT get() {
			synchronized(LazySource.this) {
				if(nextFlag) {
					nextFlag = false;
					in.set(nextValue);
					nextValue = null;
				}
			}
			return out.get();
		}
		
	}
	
	private static final long serialVersionUID = 1L;
	private final Source<OUT> out;
	private final Store<IN> in;
	
	private LazySource(Store<IN> in, Source<OUT> out) {
		this.out = out;
		this.in = in;
	}
	
	public static <T> Mapper<T, T> newLazy() {
		return newLazy(new MutableSource<>());
	}
	
	public static <IN, OUT> Mapper<IN, OUT> newLazy(Mapper<IN, OUT> mapper) {
		return newLazy(mapper, mapper);
	}
	
	public static <IN, OUT> Mapper<IN, OUT> newLazy(Store<IN> in, Source<OUT> out) {
		return new LazySource<>(in, out);
	}
	
	@Override
	public int characteristics() {
		return LazyProvider.CHARACTERISTICS;
	}
	
	@Override
	public SourceProvider<OUT> openProvider() {
		return new LazyProvider(out);
	}
	
	
	
}
