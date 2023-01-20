package com.greentree.common.util.iterator;

import java.util.Iterator;
import java.util.function.Predicate;

public interface IntIterator {

	static IntIterator build(int...iter) {
		return new IntIterator() {
			int s = 0;
			@Override
			public boolean hasNext() {
				return s < iter.length;
			}

			@Override
			public int next() {
				return iter[s++];
			}
		};
	}
	static IntIterator build(Iterable<Integer> iter) {
		return build(iter.iterator());
	}
	static IntIterator build(Iterator<Integer> iter) {
		return new IntIterator() {

			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}

			@Override
			public int next() {
				return iter.next();
			}
		};
	}

	@SuppressWarnings("unchecked")
	static Iterator<Integer>[] toIterators(IntIterator[] iters) {
		final Iterator<Integer>[] res = new Iterator[iters.length];
		for(int i = 0; i < iters.length; i++) res[i] = iters[i].toIterator();
		return res;
	}

	static IntIterator union(IntIterator...iters) {
		return build(IteratorUtil.union(toIterators(iters)));
	}
	default IntIterator filter(Predicate<Integer> predicate) {
		return build(new FilterIterator<>(toIterator(), predicate));
	}

	boolean hasNext();

	int next();
	default int size() {
		return IteratorUtil.size(toIterator());
	}
	default Iterator<Integer> toIterator() {
		return new Iterator<>() {

			@Override
			public boolean hasNext() {
				return IntIterator.this.hasNext();
			}

			@Override
			public Integer next() {
				return IntIterator.this.next();
			}
		};
	}

}
