package com.greentree.commons.assets.value.store;

import java.io.Serializable;
import java.util.function.Function;

public interface Store<T, R> extends Serializable, AutoCloseable {
	
	@Override
	void close();
	
	R result();
	void result(R result);
	void source(T source);
	Store<T, R> copy();
	
	void init(Function<T, R> function);
	
}
