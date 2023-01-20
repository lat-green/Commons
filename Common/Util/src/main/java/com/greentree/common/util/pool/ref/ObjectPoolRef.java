package com.greentree.common.util.pool.ref;


/**
 * @author Arseny Latyshev
 *
 */
public interface ObjectPoolRef<T> extends AutoCloseable {
	@Override
	void close();
	T get();
}
