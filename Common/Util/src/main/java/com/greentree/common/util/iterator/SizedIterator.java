package com.greentree.common.util.iterator;

import java.util.Iterator;

public interface SizedIterator<T> extends Iterator<T> {
	
	int size();
	
	
	
}
