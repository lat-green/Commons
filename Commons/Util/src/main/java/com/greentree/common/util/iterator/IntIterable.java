package com.greentree.common.util.iterator;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

public interface IntIterable extends Iterable<Integer> {

	static IntIterable build(Iterable<Integer> iter) {
		if(iter instanceof IntIterable) return (IntIterable) iter;
		return new IntIterable() {

			@Override
			public Iterator<Integer> iterator() {
				return iter.iterator();
			}

		};
	}

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
	static IntIterable union(IntIterable...iters) {
		return build(IteratorUtil.union(iters));
	}

	default IntIterable filter(Predicate<Integer> predicate) {
		return build(IteratorUtil.filter(this, predicate));
	}

	default IntIterator getIterator() {
		return IntIterator.build(iterator());
	}
	@Override
	default Iterator<Integer> iterator() {
		return getIterator().toIterator();
	}

	default <R> Iterable<R> map(Function<Integer, R> func) {
		return ()->IteratorUtil.map(IntIterable.this.iterator(), func);
	}

}
