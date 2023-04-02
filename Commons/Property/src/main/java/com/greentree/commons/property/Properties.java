package com.greentree.commons.property;


public class Properties {
	
	public static <T> Property<T> newStore(T value) {
		return new StoreProperty<T>(value);
	}
	
	public static <T> Property<T> newConst(T value) {
		return new ConstProperty<T>(value);
	}
	
}
