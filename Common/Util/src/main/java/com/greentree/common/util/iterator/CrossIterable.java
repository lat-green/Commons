package com.greentree.common.util.iterator;

import java.util.Iterator;
import java.util.Objects;

public class CrossIterable<T> implements Iterable<T> {

	private final Iterable<? extends T> a, b;

	public CrossIterable(Iterable<? extends T> a, Iterable<? extends T> b) {
		if(IteratorUtil.size(a) > IteratorUtil.size(b)) {
			this.a = a;
			this.b = b;
		}else {
			this.a = b;
			this.b = a;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		CrossIterable<?> other = (CrossIterable<?>) obj;
		return Objects.equals(a, other.a) && Objects.equals(b, other.b);
	}

	@Override
	public int hashCode() {
		return Objects.hash(a, b);
	}

	@Override
	public Iterator<T> iterator() {
		return IteratorUtil.cross(a.iterator(), b.iterator());
	}

	@Override
	public String toString() {
		return "CrossIterable [a=" + a + ", b=" + b + "]";
	}



}
