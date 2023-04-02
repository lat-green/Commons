package com.greentree.commons.property;

import java.util.Objects;
import java.util.function.Consumer;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observer.object.EventAction;

final class StoreLongProperty implements LongProperty {
	
	public static final int CHARACTERISTICS = CACHE | NOT_BE_CONST;
	
	private long value;
	
	private EventAction<Long> action = new EventAction<>();
	
	public StoreLongProperty(long value) {
		set(value);
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public ListenerCloser addListener(Consumer<? super Long> listener) {
		return action.addListener(listener);
	}
	
	@Override
	public long get() {
		return value;
	}
	public void event() {
		action.event(value);
	}
	
	public void set(long value) {
		Objects.requireNonNull(value);
		this.value = value;
		action.event(value);
	}
	
}
