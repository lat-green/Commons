package com.greentree.commons.graph;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import com.greentree.commons.util.iterator.IteratorUtil;

public final class RootTreeBase<V> implements MutableRootTree<V> {
	
	private static final long serialVersionUID = 1L;
	
	private final Map<V, NodeInfo<V>> infos = new HashMap<>();
	
	
	public RootTreeBase(V root) {
		infos.put(root, new NodeInfo<>(root));
	}
	
	@Override
	public void add(V vertex, V parent) {
		if(!infos.containsKey(parent))
			throw new IllegalArgumentException();
		
		final var p_info = infos.get(parent);
		
		if(infos.containsKey(vertex)) {
			final var info = infos.get(vertex);
			info.parent.remove(info);
			info.parent = p_info;
			p_info.add(info);
		}else {
			final var info = new NodeInfo<>(p_info, vertex);
			infos.put(vertex, info);
		}
	}
	
	@Override
	public void clear() {
		final var root = getRoot();
		infos.clear();
		infos.put(root, new NodeInfo<>(root));
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof RootTreeBase))
			return false;
		RootTreeBase<?> other = (RootTreeBase<?>) obj;
		return Objects.equals(infos, other.infos);
	}
	
	@Override
	public Iterable<? extends V> getChildren(Object v) {
		return IteratorUtil.map(infos.get(v), i->i.value);
	}
	
	@Override
	public V getParent(Object v) {
		return infos.get(v).parent.value;
	}
	
	@Override
	public boolean has(Object vertex) {
		return infos.containsKey(vertex);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(infos);
	}
	
	@Override
	public Iterator<V> iterator() {
		return infos.keySet().iterator();
	}
	
	@Override
	public void remove(V vertex) {
		final var info = infos.get(vertex);
		if(info.isRoot())
			throw new IllegalArgumentException();
		for(var c : info)
			remove(c.value);
		info.parent.remove(info);
		infos.remove(vertex);
	}
	
	@Override
	public String toString() {
		return "RootTreeBase " + infos;
	}
	
	private static final class NodeInfo<V> implements Iterable<NodeInfo<V>>, Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private final V value;
		
		private NodeInfo<V> parent, first, next;
		
		public NodeInfo(NodeInfo<V> parent, V value) {
			this.parent = parent;
			this.value = value;
			parent.add(this);
		}
		
		public NodeInfo(V value) {
			this.parent = this;
			this.value = value;
		}
		
		public void add(NodeInfo<V> info) {
			if(first == null) {
				first = info;
				return;
			}
			var n = first;
			while(n != null) {
				if(n.next == null) {
					n.next = info;
					return;
				}
				n = n.next;
			}
		}
		
		@Override
		public boolean equals(Object obj) {
			if(this == obj)
				return true;
			if(!(obj instanceof NodeInfo))
				return false;
			NodeInfo<?> other = (NodeInfo<?>) obj;
			return Objects.equals(first, other.first) && Objects.equals(next, other.next)
					&& Objects.equals(value, other.value);
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(first, next, parent, value);
		}
		public boolean isRoot() {
			return this == parent;
		}
		
		@Override
		public Iterator<NodeInfo<V>> iterator() {
			return new Iterator<>() {
				
				NodeInfo<V> n = first;
				
				@Override
				public boolean hasNext() {
					return n != null;
				}
				
				@Override
				public NodeInfo<V> next() {
					final var result = n;
					n = n.next;
					return result;
				}
				
			};
		}
		
		public void remove(NodeInfo<V> info) {
			if(first == null)
				return;
			if(first == info) {
				first = first.next;
				return;
			}
			var n = first;
			while(n != null) {
				if(n.next == info) {
					n.next = n.next.next;
					return;
				}
				n = n.next;
			}
		}
		
		@Override
		public String toString() {
			var result = new StringBuilder("[");
			result.append(value);
			if(first != null)
				result.append(", first=").append(first.value);
			if(next != null)
				result.append(", next=").append(next.value);
			result.append("]");
			return result.toString();
		}
		
	}
	
}
