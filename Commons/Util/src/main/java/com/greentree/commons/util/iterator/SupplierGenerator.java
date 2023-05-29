package com.greentree.commons.util.iterator;

import java.io.Serializable;
import java.util.Iterator;
import java.util.function.Supplier;

public class SupplierGenerator<T> implements Iterator<T>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean hasNext;
	private final Supplier<Supplier<T>> iter;
	private T next;
	
	
	public SupplierGenerator(Supplier<Supplier<T>> iter) {
		this.iter = iter;
		next();
	}
	
	@Override
	public boolean hasNext() {
		return hasNext;
	}
	
	@Override
	public T next() {
		final var res = next;
		final var sup = iter.get();
		if(sup != null) {
			next = sup.get();
			hasNext = true;
		}else {
			next = null;
			hasNext = false;
		}
		return res;
	}
	
}
