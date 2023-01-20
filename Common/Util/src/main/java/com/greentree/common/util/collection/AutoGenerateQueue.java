package com.greentree.common.util.collection;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

public abstract class AutoGenerateQueue<E> extends ArrayDeque<E> implements Queue<E>, Serializable {

	private static final long serialVersionUID = 1L;

	public AutoGenerateQueue() {
	}
	public AutoGenerateQueue(Collection<? extends E> c) {
		super(c);
	}
	public AutoGenerateQueue(int numElements) {
		super(numElements);
	}
	@Override
	public E element() {
		return peek();
	}
	@Override
	public E peek() {
		E e = super.peek();
		if(e == null) {
			e = generate();
			offer(e);
		}
		return e;
	}

	@Override
	public final E poll() {
		E e = super.poll();
		if(e == null) e = generate();
		return e;
	}

	@Override
	public E remove() {
		return poll();
	}

	protected abstract E generate();


}
