package com.greentree.common.util.cortege;

import java.io.Serializable;
import java.util.Objects;

public class Pair<T1, T2> implements Serializable {
	private static final long serialVersionUID = 1L;

	public T1 first;
	public T2 seconde;

	public Pair() {
	}

	public Pair(final Pair<T1, T2> pair) {
		this.first = pair.first;
		this.seconde = pair.seconde;
	}

	public Pair(final T1 first, final T2 second) {
		this.first = first;
		this.seconde = second;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof Pair)) return false;
		Pair other = (Pair) obj;
		return Objects.equals(first, other.first) && Objects.equals(seconde, other.seconde);
	}

	public Pair<T2, T1> getInverse() {
		return new Pair<>(seconde, first);
	}

	@Override
	public int hashCode() {
		return Objects.hash(first, seconde);
	}

	@Override
	public String toString() {
		return "{" + first + ", " + seconde + "}";
	}

}
