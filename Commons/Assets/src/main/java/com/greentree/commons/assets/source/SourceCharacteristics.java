package com.greentree.commons.assets.source;


public interface SourceCharacteristics<T> {
	
	int NOT_NULL = 0x00000001;
	int BLANCK_CLOSE = 0x00000002;
	
	int CHANGE_NO_EQUALS = 0x00000004;
	
	int CONST = 0x00000008;
	
	int characteristics();
	T get();
	
	default boolean hasCharacteristics(int characteristics) {
		return (characteristics() & characteristics) == characteristics;
	}
	
	default boolean hasConst() {
		return hasCharacteristics(CONST);
	}
	
	default boolean isNull() {
		return !hasCharacteristics(NOT_NULL) && get() == null;
	}
}
