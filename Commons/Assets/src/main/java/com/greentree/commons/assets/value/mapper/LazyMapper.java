package com.greentree.commons.assets.value.mapper;

import com.greentree.commons.assets.value.MutableValue;


public final class LazyMapper<T, R> implements Mapper<T, R> {
	
	private static final long serialVersionUID = 1L;
	
	private final Mapper<T, R> mapper;
	
	private transient boolean setFlag;
	private transient T nextValue;
	
	private LazyMapper(Mapper<T, R> mapper) {
		this.mapper = mapper;
	}
	
	@Override
	public LazyMapper<T, R> copy() {
		return newValue(mapper);
	}
	
	public static <T> LazyMapper<T, T> newValue() {
		return new LazyMapper<>(new MutableValue<>());
	}
	
	public static <T, R> LazyMapper<T, R> newValue(Mapper<T, R> mapper) {
		return new LazyMapper<>(mapper.copy());
	}
	
	@Override
	public R get() {
		synchronized(this) {
			if(setFlag) {
				setFlag = false;
				mapper.set(nextValue);
				nextValue = null;
			}
			return mapper.get();
		}
	}
	
	@Override
	public void set(T value) {
		synchronized(this) {
			setFlag = true;
			nextValue = value;
		}
	}
	
	@Override
	public String toString() {
		if(setFlag)
			return "Lazy [nextValue=" + nextValue + ", " + mapper + "]";
		return "Lazy [" + mapper + "]";
	}
	
	@Override
	public void close() {
		mapper.close();
	}
	
}
