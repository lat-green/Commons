package com.greentree.common.util.pool.ref;

import java.util.Objects;

public abstract class AbstractObjectPoolRef<E> implements ObjectPoolRef<E> {


	protected final E element;

	public AbstractObjectPoolRef(E element) {
		this.element = element;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		AbstractObjectPoolRef<?> other = (AbstractObjectPoolRef<?>) obj;
		return Objects.equals(element, other.element);
	}

	@Override
	public E get() {
		return element;
	}

	@Override
	public int hashCode() {
		return Objects.hash(element);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractObjectPoolRef [element=");
		builder.append(element);
		builder.append("]");
		return builder.toString();
	}

}
