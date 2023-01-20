package com.greentree.common.util.pool;

import java.lang.ref.SoftReference;
import java.util.Stack;

import com.greentree.common.util.pool.ref.AddObjectPoolRef;
import com.greentree.common.util.pool.ref.ObjectPoolRef;

public final class AddSoftStackObjectPool<E> implements AddObjectPool<E> {

	private final Stack<SoftReference<E>> objects = new Stack<>();

	@Override
	public void add(E e) {
		if(objects.contains(e))
			throw new IllegalArgumentException();
		objects.add(new SoftReference<>(e));
	}

	@Override
	public ObjectPoolRef<E> get() {
		if(objects.isEmpty()) return null;
		final var ref = objects.pop();
		final var e = ref.get();
		if(e == null)
			return get();
		return new AddObjectPoolRef<>(this, e);
	}

	@Override
	public boolean isEmpty() {
		return objects.isEmpty();
	}

}
