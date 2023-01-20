package com.greentree.commons.graph.algorithm.path;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.greentree.commons.graph.algorithm.walk.GraphVisitor;

public abstract class AbstractPathVistor<V> implements GraphVisitor<V> {
	
	protected final V end;
	private final Stack<V> path = new Stack<>();
	private boolean close;
	
	protected AbstractPathVistor(V end) {
		this.end = end;
	}
	
	@Override
	public boolean startVisit(V v) {
		path.add(v);
		if(close)
			return false;
		return true;
	}
	
	@Override
	public void endVisit(V v) {
		if(v.equals(end))
			if(!add(new ArrayList<>(path)))
				close();
		path.pop();
	}
	
	private void close() {
		close = true;
	}
	
	protected abstract boolean add(List<V> path);
	
}
