package com.greentree.commons.assets.source;

import java.util.Objects;

import com.greentree.commons.assets.mapper.Mapper;
import com.greentree.commons.assets.source.provider.SourceProvider;

public final class MutableSource<T> implements Mapper<T, T> {
	
	
	private static final long serialVersionUID = 1L;
	
	private T value;
	
	public MutableSource() {
	}
	
	public MutableSource(T value) {
		set(value);
	}
	
	@Override
	public int characteristics() {
		return MutableChangeListener.CHARACTERISTICS;
	}
	
	@Override
	public void set(T value) {
		this.value = value;
	}
	
	@Override
	public SourceProvider<T> openProvider() {
		return new MutableChangeListener();
	}
	
	@Override
	public String toString() {
		return "Mutable [" + value + "]";
	}
	
	private final class MutableChangeListener implements SourceProvider<T> {
		
		public static final int CHARACTERISTICS = BLANCK_CLOSE;
		
		private T oldValue = value;
		
		@Override
		public int characteristics() {
			return CHARACTERISTICS;
		}
		
		@Override
		public T get() {
			oldValue = value;
			return oldValue;
		}
		
		@Override
		public boolean isChenge() {
			return Objects.equals(oldValue, value);
		}
		
	}
	
}
