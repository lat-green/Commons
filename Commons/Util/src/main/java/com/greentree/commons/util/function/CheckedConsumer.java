package com.greentree.commons.util.function;

import java.util.Objects;
import java.util.function.Consumer;

import com.greentree.commons.util.exception.WrappedException;

@FunctionalInterface
public interface CheckedConsumer<T> extends Consumer<T> {
	
	@SuppressWarnings("unchecked")
	static <T> CheckedConsumer<T> build(Consumer<? super T> consumer) {
		if(consumer instanceof CheckedConsumer)
			return (CheckedConsumer<T>) consumer;
		return t->consumer.accept(t);
	}
	
	@Override
	default void accept(T t) {
		try {
			checkedAccept(t);
		}catch(Exception e) {
			throw new WrappedException(e);
		}
	}
	
	void checkedAccept(T t) throws Exception;
	
	@Deprecated
	default Consumer<T> toNonCheked() {
		return this;
	}
	
	default CheckedConsumer<T> andThen(CheckedConsumer<? super T> after) {
		Objects.requireNonNull(after);
		return (T t)-> {
			checkedAccept(t);
			after.checkedAccept(t);
		};
	}
	
	default CheckedConsumer<T> andThen(Consumer<? super T> after) {
		Objects.requireNonNull(after);
		return (T t)-> {
			checkedAccept(t);
			after.accept(t);
		};
	}
	
}
