package com.greentree.commons.util.pool;

import java.lang.ref.SoftReference;
import java.util.Stack;

import com.greentree.commons.util.pool.ref.AbstractObjectPoolRef;
import com.greentree.commons.util.pool.ref.ObjectPoolRef;

public abstract class GenerateSoftStackObjectPool<E> implements GenerateObjectPool<E> {

	private final Stack<SoftReference<E>> objects = new Stack<>();

	@Override
	public ObjectPoolRef<E> get() {
		if(objects.isEmpty())
			return ref(generate());
		final var ref = objects.pop();
		final var e = ref.get();
		if(e == null)
			return get();
		return ref(e);
	}

	@Override
	public boolean isEmpty() {
		return objects.isEmpty();
	}

	private void add(E e) {
		if(objects.contains(e))
			throw new IllegalArgumentException();
		objects.add(new SoftReference<>(e));
	}

	private ObjectPoolRef<E> ref(E element) {
		return new AbstractObjectPoolRef<>(element) {

			@Override
			public void close() {
				add(element);
			}
		};
	}
}
