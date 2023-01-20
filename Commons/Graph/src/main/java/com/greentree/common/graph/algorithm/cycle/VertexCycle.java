package com.greentree.common.graph.algorithm.cycle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VertexCycle<E> extends ArrayList<E> {

	private static final long serialVersionUID = 1L;

	public VertexCycle() {

	}

	@SafeVarargs
	public VertexCycle(E...cycle) {
		super(Arrays.asList(cycle));
	}

	public VertexCycle(List<E> cycle) {
		super(cycle);
	}

	private static <E> boolean equals(VertexCycle<E> a, VertexCycle<E> b, int s) {
		int offset = b.indexOf(a.get(0));
		if(offset == -1)return false;

		for(int i = 0; i < s; i++)
			if(!a.get(i).equals(b.get((i + offset) % s))) return false;

		return true;
	}

	private static <E> boolean inverseEquals(VertexCycle<E> a, VertexCycle<E> b, int s) {
		int offset = b.indexOf(a.get(0));
		if(offset == -1)return false;

		for(int i = 0; i < s; i++)
			if(!a.get(i).equals(b.get((s-i + offset) % s))) return false;

		return true;

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (getClass() != obj.getClass()) return false;

		VertexCycle<E> other = (VertexCycle<E>) obj;

		int s = size();
		if(s != other.size()) return false;

		return equals(this, other, s) || inverseEquals(this, other, s);
	}

	@Override
	public int hashCode() {
		int hash = 31;
		for(var a : this)hash += a.hashCode();
		return hash;
	}

	public JointCycle<E> toJoints() {
		return new JointCycle<>(this);
	}

}

