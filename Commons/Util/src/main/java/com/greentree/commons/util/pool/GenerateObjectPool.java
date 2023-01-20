package com.greentree.commons.util.pool;

public interface GenerateObjectPool<E> extends ObjectPool<E> {

	E generate();
}
