package com.greentree.commons.util.pool.ref;

import java.util.Objects;

import com.greentree.commons.util.pool.AddObjectPool;

public final class AddObjectPoolRef<E> extends AbstractObjectPoolRef<E> implements ObjectPoolRef<E> {

	private AddObjectPool<E> pool;

	public AddObjectPoolRef(AddObjectPool<E> pool, E element) {
		super(element);
		this.pool = pool;
	}

	@Override
	public void close() {
		pool.add(element);
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		AddObjectPoolRef<?> other = (AddObjectPoolRef<?>) obj;
		return super.equals(other) && Objects.equals(pool, other.pool);
	}

	@Override
	public int hashCode() {
		return Objects.hash(element, pool);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AddObjectPoolRef [element=");
		builder.append(element);
		builder.append(", pool=");
		builder.append(pool);
		builder.append("]");
		return builder.toString();
	}


}
