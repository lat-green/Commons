package com.greentree.commons.util.pool;

public interface AddObjectPool<E> extends ObjectPool<E> {

	void add(E e);
}
