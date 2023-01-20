package com.greentree.common.graph.algorithm.cycle;

import java.util.ArrayList;
import java.util.List;

import com.greentree.common.graph.DirectedArc;

public class JointCycle<E> extends ArrayList<DirectedArc<E>> {

	private static final long serialVersionUID = 1L;

	public JointCycle(List<E> cycle) {
		for(int i = 0; i < cycle.size()-1; i++) add(new DirectedArc<>(cycle.get(i), cycle.get(i+1)));
		add(new DirectedArc<>(cycle.get(cycle.size()-1), cycle.get(0)));
		//		add(new Joint<>(cycle.get(0), cycle.get(cycle.size()-1)));
	}

	@Override
	public boolean add(DirectedArc<E> arg0) {
		//		if(contains(arg0))return false;
		return super.add(arg0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object obj) {
		if(!(obj instanceof DirectedArc))return false;
		DirectedArc<E> other = (DirectedArc<E>) obj;
		for(DirectedArc<E> a : this)if(other.equals(a))return true;
		return false;
	}

	public List<DirectedArc<E>> cross(JointCycle<E> cycle){
		List<DirectedArc<E>> res = new ArrayList<>();
		for(DirectedArc<E> v : cycle)if(contains(v))res.add(v);
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		JointCycle<E> other = (JointCycle<E>) obj;
		if(size() != other.size()) return false;

		for(DirectedArc<E> a : this)if(!other.contains(a))return false;

		return true;
	}

	public VertexCycle<E> toVertexs() {
		VertexCycle<E> res = new VertexCycle<>();
		for(var j : this)res.add(j.begin());
		return res;
	}

}

