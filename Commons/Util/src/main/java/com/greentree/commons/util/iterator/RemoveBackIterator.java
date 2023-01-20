package com.greentree.commons.util.iterator;

import java.io.Serializable;
import java.util.Iterator;

public class RemoveBackIterator<T> implements Iterator<T>, Serializable {
	private static final long serialVersionUID = 1L;

	private boolean hasNext;
	private final Iterator<T> iterator;
	private T next;

	public RemoveBackIterator(Iterator<T> iterator) {
		this.iterator = iterator;
		hasNext = true;
		next();
	}

	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public T next() {
		final var res = next;
		if(iterator.hasNext()) {
			next = iterator.next();
			if(!iterator.hasNext()) {
				next = null;
				hasNext = false;
			}
		}
		return res;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RemoveBackIterator [iterator=");
		builder.append(iterator);
		builder.append("]");
		return builder.toString();
	}

}
