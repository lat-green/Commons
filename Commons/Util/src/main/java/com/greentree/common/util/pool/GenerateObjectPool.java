package com.greentree.common.util.pool;

public interface GenerateObjectPool<E> extends ObjectPool<E> {

	E generate();
}
