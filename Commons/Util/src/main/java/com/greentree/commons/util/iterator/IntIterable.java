package com.greentree.commons.util.iterator;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

public interface IntIterable extends Iterable<Integer> {

	/**
	 * Builds IntIterable from iterable.
	 *
	 * @param iter iterable of integers
	 * @return IntIterable
	 */
	static IntIterable build(Iterable<Integer> iter) {
		if(iter instanceof IntIterable) return (IntIterable) iter;
		return new IntIterable() {

			@Override
			public Iterator<Integer> iterator() {
				return iter.iterator();
			}

		};
	}

	/**
	 * Creates IntIterable by mapping elements.
	 *
	 * @param iterable source iterable
	 * @param func mapping function
	 * @param <T> source type
	 * @return mapped Int Iterable
	 */
	static <T> IntIterable map(Iterable<T> iterable, Function<T, Integer> func) {
		return new IntIterable() {

			@Override
			public IntIterator getIterator() {
				final var iter = iterable.iterator();
				return new IntIterator() {

					@Override
					public boolean hasNext() {
						return iter.hasNext();
					}

					@Override
					public int next() {
						return func.apply(iter.next());
					}
				};
			}

		};
	}

	/**
	 * Returns union of multiple IntIterables.
	 *
	 * @param iters iterables to union
	 * @return union iterable
	 */
	static IntIterable union(IntIterable...iters) {
		return build(IteratorUtil.union(iters));
	}

	/**
	 * Returns filtered iterable.
	 *
	 * @param predicate filter predicate
	 * @return filtered iterable
	 */
	default IntIterable filter(Predicate<Integer> predicate) {
		return build(IteratorUtil.filter(this, predicate));
	}

	/**
	 * Returns IntIterator.
	 *
	 * @return IntIterator
	 */
	default IntIterator getIterator() {
		return IntIterator.build(iterator());
	}

	/**
	 * Returns standard iterator.
	 *
	 * @return standard iterator
	 */
	@Override
	default Iterator<Integer> iterator() {
		return getIterator().toIterator();
	}

	/**
	 * Returns mapped iterable.
	 *
	 * @param func mapping function
	 * @param <R> result type
	 * @return mapped iterable
	 */
	default <R> Iterable<R> map(Function<Integer, R> func) {
		return ()->IteratorUtil.map(IntIterable.this.iterator(), func);
	}

}
