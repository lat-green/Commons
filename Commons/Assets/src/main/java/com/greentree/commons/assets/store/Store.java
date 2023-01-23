package com.greentree.commons.assets.store;

import java.io.Serializable;

public interface Store<T> extends Serializable {
	
	void set(T value);
	
}
