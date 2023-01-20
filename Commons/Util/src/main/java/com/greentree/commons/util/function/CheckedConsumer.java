package com.greentree.commons.util.function;

import java.util.Objects;
import java.util.function.Consumer;

import com.greentree.commons.util.exception.WrappedException;

@FunctionalInterface
public interface CheckedConsumer<T> {
	
	void accept(T t) throws Exception;
	
	default Consumer<T> toNonCheked() {
		return t-> {
			try {
				accept(t);
			}catch(Exception e) {
				throw new WrappedException(e);
			}
		};
	}
	
	default CheckedConsumer<T> andThen(CheckedConsumer<? super T> after) {
		Objects.requireNonNull(after);
		return (T t)-> {
			accept(t);
			after.accept(t);
		};
	}
	
	default CheckedConsumer<T> andThen(Consumer<? super T> after) {
		Objects.requireNonNull(after);
		return (T t)-> {
			accept(t);
			after.accept(t);
		};
	}
	
}
