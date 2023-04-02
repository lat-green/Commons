package com.greentree.commons.property;

import java.util.function.Consumer;

import com.greentree.commons.action.ListenerCloser;


record ConstProperty<T>(T value) implements Property<T> {
	
	public static final int CHARACTERISTICS = CONST | BLANCK_CLOSE;
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public ListenerCloser addListener(Consumer<? super T> listener) {
		return ListenerCloser.EMPTY;
	}
	
	@Override
	public T get() {
		return value;
	}
	
}
