package com.greentree.commons.web._private;

import java.util.Arrays;

public final class ByteQueue {

	@Override
	public String toString() {
		final var arr = new byte[size()];
		new ByteQueue(this).pop(arr, 0, arr.length);
		return Arrays.toString(arr);
	}
	
	private final byte[] arr;

	private int begin, end;

	public ByteQueue(ByteQueue queue) {
		arr = queue.arr.clone();
		this.begin = queue.begin;
		this.end = queue.end;
	}
	public ByteQueue(int length) {
		arr = new byte[length];
	}
	public synchronized void push(byte b) {
		if(end == -1) throw new IllegalArgumentException();
		arr[end] = b;
		end++;
		if(end > begin) {
			if(end >= arr.length) 
				end -= arr.length;
		}
		if(begin == end) end = -1;
	}

	public synchronized int size() {
		if(end == -1) return arr.length;
		return (end - begin + arr.length) % arr.length;
	}

	public synchronized byte pop() {
		final var res = arr[begin];
		begin = (begin + 1) % arr.length;
		return res;
	}
	
	public synchronized boolean isEmpty() {
		return begin == end;
	}
	public synchronized void clear() {
		end = begin;
	}

	public void push(ByteQueue other, int count) {
		while(count-- > 0) {
			push(other.pop());
		}
	}
	public void push(ByteQueue other) {
		push(other, other.size());
	}
	public void push(byte[] buf, int off, int cnt) {
		for(int i = 0; i < cnt; i++)
			push(buf[i + off]);
	}
	
	public void pop(byte[] buf, int off, int cnt) {
		for(int i = 0; i < cnt; i++)
			buf[i + off] = pop();
	}

	public void pop(byte[] buf) {
		pop(buf, 0, buf.length);
	}
	public void remove(int count) {
		while(count-- > 0) pop();
	}

	public synchronized void peek(byte[] buf, int off, int cnt) {
		for(int i = 0; i < cnt; i++)
			buf[off + i] = arr[(begin + i) % arr.length];
	}
	
	public int free() {
		return arr.length - size();
	}
	
	public boolean isFull() {
		return free() == 0;
	}
	public int maxCount() {
		return arr.length;
	}
	public int fill(byte[] buf, int off) {
		final var s = arr.length - size();
		push(buf, off, s);
		return s;
	}

}