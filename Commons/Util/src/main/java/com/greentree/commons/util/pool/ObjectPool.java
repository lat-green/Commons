package com.greentree.commons.util.pool;

import com.greentree.commons.util.pool.ref.ObjectPoolRef;

/**
 * @author Arseny Latyshev
 *
 */
public interface ObjectPool<E> {

	ObjectPoolRef<E> get();
	boolean isEmpty();

}
