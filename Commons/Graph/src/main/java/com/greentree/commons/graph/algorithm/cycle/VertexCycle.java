package com.greentree.commons.graph.algorithm.cycle;

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

	private boolean cycleEquals(VertexCycle<? extends E> other) {
		int offset = other.indexOf(get(0));
		if(offset == -1)return false;

		var size = size();
		if(size != other.size())
			return false;
		
		for(int i = 0; i < size; i++)
			if(!get(i).equals(other.get((i + offset) % size)))
				return false;

		return true;
	}

	private boolean cycleEqualsInverse(VertexCycle<? extends E> other) {
		int offset = other.indexOf(get(0));
		if(offset == -1)return false;

		var size = size();
		if(size != other.size())
			return false;
		
		for(int i = 0; i < size; i++)
			if(!get(i).equals(other.get((size - i + offset) % size)))
				return false;

		return true;

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (getClass() != obj.getClass()) return false;

		var other = (VertexCycle<E>) obj;

		return cycleEquals(other) || cycleEqualsInverse(other);
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

