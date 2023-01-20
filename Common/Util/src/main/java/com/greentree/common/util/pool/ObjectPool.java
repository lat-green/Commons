package com.greentree.common.util.pool;

import com.greentree.common.util.pool.ref.ObjectPoolRef;

/**
 * @author Arseny Latyshev
 *
 */
public interface ObjectPool<E> {

	ObjectPoolRef<E> get();
	boolean isEmpty();

}
