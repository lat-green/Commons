package com.greentree.commons.assets.value;

import java.io.Serializable;

import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.assets.value.map.LazyValue;

public interface Value<T> extends AutoCloseable {
	
	static String toMultiLineString(Value<?> value) {
		final var str = value.toString();
		String result = "";
		int t = 0;
		for(int i = 0; i < str.length(); i++) {
			final var c = str.charAt(i);
			switch(c) {
				case ',' -> {
					i++;
					for(; i < str.length(); i++) {
						final var j = str.charAt(i);
						if(j != ' ')
							break;
					}
					i--;
					result += '\n' + "\t".repeat(t);
				}
				case '[' -> {
					t++;
					result += '\n' + "\t".repeat(t);
				}
				case ']' -> {
					t--;
					i++;
					for(; i < str.length(); i++) {
						final var j = str.charAt(i);
						if(j == ']')
							t--;
						else
							break;
					}
					result += '\n' + "\t".repeat(t);
				}
				default -> result += c;
			}
		}
		return result;
	}
	
	
	
	@Override
	default void close() {
	}
	
	T get();
	
	default boolean isConst() {
		return false;
	}
	
	default boolean isNull() {
		return get() == null;
	}
	
	ObjectObservable<T> observer();
	
	default Value<T> toConst() {
		return ConstValue.newValue(get());
	}
	
	Serializable toSerializable();
	
	default Value<T> toLazy() {
		return ConstWrappedValue.newValue(this, LazyValue.newValue());
	}
	
}
