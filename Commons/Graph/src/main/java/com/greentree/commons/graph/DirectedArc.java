package com.greentree.commons.graph;

import java.util.Objects;

public class DirectedArc<E> {
	
	private final E begin, end;
	
	public DirectedArc(E begin, E end) {
		if(begin.equals(end))
			throw new IllegalArgumentException("DirectedArc a==b (" + begin + ")");
		this.begin = Objects.requireNonNull(begin);
		this.end = Objects.requireNonNull(end);
	}
	
	
	public E begin() {
		return begin;
	}
	
	
	public E end() {
		return end;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof DirectedArc))
			return false;
		DirectedArc<?> other = (DirectedArc<?>) obj;
		return Objects.equals(begin, other.begin) && Objects.equals(end, other.end);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(begin, end);
	}
	
	@Override
	public String toString() {
		return "(" + begin + " " + end + ")";
	}
	
}
