package com.greentree.commons.util.iterator;

import java.util.Iterator;
import java.util.function.Predicate;

public interface IntIterator {

	/**
	 * Builds iterator from varargs.
	 *
	 * @param iter int values
	 * @return iterator
	 */
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

	/**
	 * Builds iterator from iterable.
	 *
	 * @param iter iterable of integers
	 * @return iterator
	 */
	static IntIterator build(Iterable<Integer> iter) {
		return build(iter.iterator());
	}

	/**
	 * Builds iterator from iterator.
	 *
	 * @param iter iterator of integers
	 * @return iterator
	 */
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

	/**
	 * Converts array of IntIterator to array of Iterator.
	 *
	 * @param iters array of IntIterator
	 * @return array of Iterator
	 */
	@SuppressWarnings("unchecked")
	static Iterator<Integer>[] toIterators(IntIterator[] iters) {
		final Iterator<Integer>[] res = new Iterator[iters.length];
		for(int i = 0; i < iters.length; i++) res[i] = iters[i].toIterator();
		return res;
	}

	/**
	 * Returns union of multiple iterators.
	 *
	 * @param iters iterators to union
	 * @return union iterator
	 */
	static IntIterator union(IntIterator...iters) {
		return build(IteratorUtil.union(toIterators(iters)));
	}

	/**
	 * Returns filtered iterator.
	 *
	 * @param predicate filter predicate
	 * @return filtered iterator
	 */
	default IntIterator filter(Predicate<Integer> predicate) {
		return build(new FilterIterator<>(toIterator(), predicate));
	}

	/**
	 * Returns true if more elements remain.
	 *
	 * @return true if has more elements
	 */
	boolean hasNext();

	/**
	 * Returns next integer.
	 *
	 * @return next integer
	 */
	int next();

	/**
	 * Returns size of iterator.
	 *
	 * @return remaining elements count
	 */
	default int size() {
		return IteratorUtil.size(toIterator());
	}

	/**
	 * Converts to standard Iterator.
	 *
	 * @return standard iterator
	 */
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
