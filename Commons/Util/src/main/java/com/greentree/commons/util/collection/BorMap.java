package com.greentree.commons.util.collection;

import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class BorMap<E> extends AbstractMap<String, E> {
	
	
	
	private static final class ByteIterator {
		
		private final byte[] bytes;
		private int index;
		
		public ByteIterator(byte[] bytes) {
			this.bytes = bytes;
		}
		
		public boolean hasNext() {
			return index < bytes.length;
		}
		
		public byte next() {
			return bytes[index++];
		}
		
	}
	
	private static class Node<E> {
		
		private static final int BYTE_SIZE = Byte.MAX_VALUE - Byte.MIN_VALUE + 1;
		
		private E value;
		@SuppressWarnings("unchecked")
		private final Node<E>[] nextNodes = new Node[BYTE_SIZE];
		private int nextNodesCount;
		
		public Set<Entry<String, E>> entrySet() {
			final var result = entrySetBuilder();
			return result.stream().map(x->Map.entry(x.getKey().toString(), x.getValue())).collect(Collectors.toSet());
		}
		
		public Set<Entry<StringBuilder, E>> entrySetBuilder() {
			final var result = new HashSet<Entry<StringBuilder, E>>();
			entrySetBuilder(new StringBuilder(), result);
			return result;
		}
		
		public void entrySetBuilder(StringBuilder parent, Set<Entry<StringBuilder, E>> dest) {
			if(value != null)
				dest.add(Map.entry(parent, value));
			for(int i = 0; i < nextNodes.length; i++) {
				final var n = nextNodes[i];
				if(n != null) {
					final var builder = new StringBuilder(parent);
					builder.append((char) (byte) i);
					n.entrySetBuilder(builder, dest);
				}
			}
		}
		
		public E put(String key, E value) {
			if(value == null)
				return remove(key);
			return put(new ByteIterator(key.getBytes(StandardCharsets.UTF_8)), value);
		}
		
		private E put(ByteIterator iterator, E value) {
			if(!iterator.hasNext()) {
				final var result = this.value;
				this.value = value;
				return result;
			}
			final var b = iterator.next();
			final var i = index(b);
			Node<E> n = nextNodes[i];
			if(n == null) {
				n = new Node<E>();
				nextNodes[i] = n;
				nextNodesCount++;
			}
			return n.put(iterator, value);
		}
		
		private boolean remove(ByteIterator iterator) {
			if(!iterator.hasNext()) {
				this.value = null;
				return true;
			}
			final var b = iterator.next();
			final var i = index(b);
			Node<E> n = nextNodes[i];
			if(n != null && n.remove(iterator)) {
				nextNodesCount--;
				nextNodes[i] = null;
				return nextNodesCount == 0;
			}
			return false;
		}
		
		private static int index(byte b) {
			int index = b;
			index += BYTE_SIZE;
			index %= BYTE_SIZE;
			return index;
		}
		
		public E get(String key) {
			return get(new ByteIterator(key.getBytes(StandardCharsets.UTF_8)));
		}
		
		private E get(ByteIterator iterator) {
			if(!iterator.hasNext()) {
				return value;
			}
			final var b = iterator.next();
			final var i = index(b);
			final var n = nextNodes[i];
			if(n == null)
				return null;
			return n.get(iterator);
		}
		
		public void remove0(String key) {
			remove(new ByteIterator(key.getBytes(StandardCharsets.UTF_8)));
		}
		
		public E remove(String key) {
			final var v = get(key);
			remove0(key);
			return v;
		}
		
	}
	
	private final Node<E> root = new Node<>();
	
	@Override
	public E put(String key, E value) {
		return root.put(key, value);
	}
	
	@Override
	public E get(Object key) {
		return root.get((String) key);
	}
	
	@Override
	public E remove(Object key) {
		return root.remove((String) key);
	}
	
	@Override
	public Set<Entry<String, E>> entrySet() {
		return root.entrySet();
	}
	
}
