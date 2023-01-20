package com.greentree.commons.util.collection;

public class MapIDImpl implements MapID {
	private static final long serialVersionUID = 1L;

	private Node node;
	private int size;
	{
		size = 0;
		node = new Node(size++);
	}

	@Override
	public void free(int i) {
		assert i >= 0   : "";
		assert i < size : "";
		node = new Node(node, size++);
	}

	@Override
	public int get() {
		final var n = node.data;
		node = node.next;
		if(node == null)
			node = new Node(null, size++);
		return n;
	}

	private static class Node {
		private int data;
		private Node next;

		public Node(int data) {
			this.data = data;
		}
		public Node(Node next, int data) {
			this.next = next;
			this.data = data;
		}


	}

}
