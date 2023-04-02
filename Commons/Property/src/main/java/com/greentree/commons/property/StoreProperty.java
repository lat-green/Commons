package com.greentree.commons.property;

import java.util.Objects;
import java.util.function.Consumer;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observer.object.EventAction;

final class StoreProperty<T> implements Property<T> {
	
	public static final int CHARACTERISTICS = CACHE | NOT_BE_CONST;
	
	private T value;
	
	private EventAction<T> action = new EventAction<>();
	
	public StoreProperty(T value) {
		set(value);
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public ListenerCloser addListener(Consumer<? super T> listener) {
		return action.addListener(listener);
	}
	
	@Override
	public T get() {
		return value;
	}
	public void event() {
		action.event(value);
	}
	
	public void set(T value) {
		Objects.requireNonNull(value);
		this.value = value;
		action.event(value);
	}
	
}
