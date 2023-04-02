package com.greentree.commons.property;

import java.util.function.Consumer;

import com.greentree.commons.action.ListenerCloser;

public interface BaseProperty<T, P extends BaseProperty<T, P>> {
	int BLANCK_CLOSE = 0x00000021;
	int CACHE = 0x00000022;
	int LAZY = 0x00000024;
	int DISTINCT_CHANGE = 0x00000028;
	
	int MAY_BE_CONST = 0x00000041;
	int WILL_BE_CONST = 0x00000042;
	int NOT_BE_CONST = 0x00000044;
	int CONST = 0x00000048;
	
	
	int characteristics();
	
	default boolean hasCharacteristics(int characteristics) {
		return (characteristics() & characteristics) == characteristics;
	}
	
	ListenerCloser addListener(Consumer<? super T> listener);
	
}
