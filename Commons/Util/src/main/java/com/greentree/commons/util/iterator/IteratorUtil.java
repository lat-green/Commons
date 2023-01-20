package com.greentree.commons.util.iterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class IteratorUtil {
	
	
	private IteratorUtil() {
		
	}
	
	public static <T> boolean all(Iterable<? extends T> iterable, Predicate<? super T> predicate) {
		return all(iterable.iterator(), predicate);
	}
	
	public static <T> boolean all(Iterator<? extends T> iter, Predicate<? super T> predicate) {
		return !any(iter, predicate.negate());
	}
	
	public static <T> boolean any(Iterable<? extends T> iterable, Predicate<? super T> predicate) {
		return any(iterable.iterator(), predicate);
	}
	
	public static <T> boolean any(Iterator<? extends T> iter, Predicate<? super T> predicate) {
		while(iter.hasNext()) {
			final var e = iter.next();
			if(predicate.test(e))
				return true;
		}
		return false;
	}
	
	@Deprecated
	public static <T> T[] array(ArrayIterable<T> iterable) {
		return iterable.toArray();
	}
	
	@Deprecated
	public static <T> T[] array(ArrayIterable<T> iterable, T[] ts) {
		final var len = min(ts.length, size(iterable));
		final var iter = iterable.iterator();
		for(int i = 0; i < len; i++)
			ts[i] = iter.next();
		return ts;
	}
	
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T> T[] array(Iterable<T> iterable) {
		if(iterable instanceof ArrayIterable)
			return array((ArrayIterable<T>) iterable);
		return (T[]) toCollection(iterable).toArray();
	}
	
	@Deprecated
	public static <T> T[] array(Iterable<T> iterable, T[] ts) {
		if(iterable instanceof ArrayIterable)
			return array((ArrayIterable<T>) iterable, ts);
		return toCollection(iterable).toArray(ts);
	}
	
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T> T[] array(Iterator<T> iterator) {
		if(iterator instanceof ArrayIterator)
			return array((ArrayIterator<T>) iterator);
		return (T[]) toCollection(iterator).toArray();
	}
	
	public static <T> Iterable<T> clone(Iterable<? extends T> ts) {
		final var res = new ArrayList<T>();
		for(var t : ts)
			res.add(t);
		return res;
	}
	
	@Deprecated
	public static <T> Iterator<T> clone(Iterator<? extends T> ts) {
		return iterator(array(ts));
	}
	
	public static <T> Iterable<? extends T> cross(Iterable<? extends Iterable<? extends T>> ts) {
		return reduce(ts, IteratorUtil::cross, IteratorUtil::empty);
	}
	
	@SafeVarargs
	public static <T> Iterable<? extends T> cross(Iterable<? extends T>... ts) {
		return cross(iterable(ts));
	}
	
	public static <T> Iterable<T> cross(Iterable<? extends T> iter1, Iterable<? extends T> iter2) {
		return new CrossIterable<>(iter1, iter2);
	}
	
	public static <T> Iterator<T> cross(Iterator<? extends T> iter1, Iterator<? extends T> iter2) {
		final var set = new HashSet<T>();
		while(iter1.hasNext())
			set.add(iter1.next());
		
		final var result = new HashSet<T>();
		while(iter2.hasNext()) {
			final var e = iter2.next();
			if(set.contains(e))
				result.add(e);
		}
		
		return result.iterator();
	}
	
	public static <T> Iterable<T> empty() {
		return EmptyIterable.instance();
	}
	
	public static boolean equals(Iterable<?> a, Iterable<?> b) {
		return equals(a.iterator(), b.iterator());
	}
	
	public static boolean equals(Iterator<?> iter1, Iterator<?> iter2) {
		boolean h1, h2;
		while(true) {
			h1 = iter1.hasNext();
			h2 = iter2.hasNext();
			if(h1 ^ h2)
				return false; // size1 != size2
			if(!h1 && !h2)
				return true;
			
			final var n1 = iter1.next();
			final var n2 = iter2.next();
			
			if(!n1.equals(n2))
				return false;
		}
	}
	
	public static <T> Iterable<T> filter(Iterable<T> iterable, Predicate<? super T> predicate) {
		return new FilterIterable<>(iterable, predicate);
	}
	
	public static <T> Iterator<T> filter(Iterator<? extends T> iterator,
			Predicate<? super T> predicate) {
		return new FilterIterator<>(iterator, predicate);
	}
	
	public static <T> Iterable<T> foreach(Iterator<T> iter) {
		return ()->iter;
	}
	
	public static <T> Iterator<T> generator(Consumer<Consumer<T>> iter) {
		return new ThreadGenerator<>(iter);
	}
	
	public static <T> Iterable<T> inverse(Iterable<T> iter) {
		return ()->inverse(iter.iterator());
	}
	
	public static <T> Iterator<T> inverse(Iterator<T> iter) {
		final var list = new LinkedList<T>();
		while(iter.hasNext())
			list.addFirst(iter.next());
		return list.iterator();
	}
	
	public static boolean isEmpty(Iterable<?> iterator) {
		return isEmpty(iterator.iterator());
	}
	
	public static boolean isEmpty(Iterator<?> iterator) {
		return !iterator.hasNext();
	}
	
	public static <T> Iterable<T> iterable(Consumer<Consumer<T>> iter) {
		return ()->iterator(iter);
	}
	
	@SafeVarargs
	public final static <T> ArrayIterable<T> iterable(int begin, int end, T... ts) {
		return new ArrayIterable<>(begin, end, ts);
	}
	
	@SafeVarargs
	public final static <T> ArrayIterable<T> iterable(int size, T... ts) {
		return new ArrayIterable<>(size, ts);
	}
	
	public static <T> Iterable<T> iterable(Iterator<T> iterator) {
		final var arr = toCollection(iterator);
		return ()->iterator(arr);
	}
	
	public static <T> Iterable<T> iterable(Supplier<Supplier<T>> iter) {
		return ()->iterator(iter);
	}
	
	@SafeVarargs
	public final static <T> ArrayIterable<T> iterable(T... ts) {
		return new ArrayIterable<>(ts);
	}
	
	public static <T> Iterator<T> iterator(Consumer<Consumer<T>> iter) {
		final var list = new ArrayList<T>();
		iter.accept(t-> {
			list.add(t);
		});
		return new Iterator<>() {
			
			private final Iterator<T> iter = list.iterator();
			
			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}
			
			@Override
			public T next() {
				if(iter.hasNext())
					return iter.next();
				return null;
			}
		};
	}
	
	@SafeVarargs
	public final static <T> ArrayIterator<T> iterator(int begin, int end, T... ts) {
		return new ArrayIterator<>(begin, end, ts);
	}
	
	@SafeVarargs
	public final static <T> ArrayIterator<T> iterator(int size, T... ts) {
		return new ArrayIterator<>(size, ts);
	}
	
	public static <T> Iterator<T> iterator(Iterable<T> iterable) {
		return iterable.iterator();
	}
	
	public static <T> Iterator<T> iterator(Supplier<Supplier<T>> iter) {
		return new SupplierGenerator<>(iter);
	}
	
	@SafeVarargs
	public final static <T> ArrayIterator<T> iterator(T... ts) {
		return new ArrayIterator<>(ts);
	}
	
	public static <T, R> Iterable<R> map(Iterable<? extends T> iter,
			Function<? super T, ? extends R> func) {
		return new FuncMapIterable<>(iter, func);
	}
	
	public static <T, R> Iterator<R> map(Iterator<? extends T> iter,
			Function<? super T, ? extends R> func) {
		return new FuncMapIterator<>(iter, func);
	}
	
	public static <T extends Comparable<? super T>> T min(Iterable<? extends T> iterable) {
		return min(iterable, T::compareTo);
	}
	
	public static <T> T min(Iterable<? extends T> iterable, Comparator<? super T> comporator) {
		return reduce(iterable, (a, b)-> {
			if(comporator.compare(a, b) < 0)
				return a;
			return b;
		});
	}
	
	public static <T> T min(Iterable<? extends T> iterable, Comparator<? super T> comporator,
			Supplier<? extends T> empty) {
		return reduce(iterable, (a, b)-> {
			if(comporator.compare(a, b) < 0)
				return a;
			return b;
		}, empty);
	}
	
	public static <T> T min(T min, Iterable<? extends T> iterable,
			Comparator<? super T> comporator) {
		return reduce(iterable, (a, b)-> {
			if(comporator.compare(a, b) < 0)
				return a;
			return b;
		}, e->min.equals(e));
	}
	
	public static <T> T min(T min, Iterable<? extends T> iterable, Comparator<? super T> comporator,
			Supplier<? extends T> empty) {
		return reduce(iterable, (a, b)-> {
			if(comporator.compare(a, b) < 0)
				return a;
			return b;
		}, e->min.equals(e), empty);
	}
	
	public static <T, U extends Comparable<? super U>> T min(U min, Iterable<? extends T> iterable,
			Function<? super T, ? extends U> func) {
		return reduce(iterable, (a, b)-> {
			final var ua = func.apply(a);
			if(min.equals(ua))
				return a;
			final var ub = func.apply(a);
			if(min.equals(ub))
				return b;
			if(ua.compareTo(ub) < 0)
				return a;
			return b;
		});
	}
	
	public static <T, U extends Comparable<? super U>> T min(U min, Iterable<? extends T> iterable,
			Function<? super T, ? extends U> func, Supplier<? extends T> empty) {
		return reduce(iterable, (a, b)-> {
			final var ua = func.apply(a);
			if(min.equals(ua))
				return a;
			final var ub = func.apply(a);
			if(min.equals(ub))
				return b;
			if(ua.compareTo(ub) < 0)
				return a;
			return b;
		}, empty);
	}
	
	public static <T, U extends Comparable<? super U>> T min(Iterable<? extends T> iterable,
			Function<? super T, ? extends U> func) {
		return min(iterable, Comparator.comparing(func));
	}
	
	public static <T, U extends Comparable<? super U>> T min(Iterable<? extends T> iterable,
			Function<? super T, ? extends U> func, Supplier<? extends T> empty) {
		return min(iterable, Comparator.comparing(func), empty);
	}
	
	public static <T, U extends Comparable<? super U>> T min(T min, Iterable<? extends T> iterable,
			Function<? super T, ? extends U> func) {
		return min(min, iterable, Comparator.comparing(func));
	}
	
	public static <T extends Comparable<T>> T min(Iterable<? extends T> iterable,
			Supplier<? extends T> empty) {
		return min(iterable, T::compareTo, empty);
	}
	
	public static <T extends Comparable<? super T>> T min(T min, Iterable<? extends T> iterable) {
		return min(min, iterable, T::compareTo);
	}
	
	@SafeVarargs
	public static <T extends Comparable<? super T>> T min(T... ts) {
		return min(iterable(ts));
	}
	
	public static <T> T reduce(Iterable<? extends T> iterable,
			BiFunction<? super T, ? super T, ? extends T> reduceFunc) {
		return reduce(iterable.iterator(), reduceFunc);
	}
	
	public static <T> T reduce(Iterable<? extends T> iterable,
			BiFunction<? super T, ? super T, ? extends T> reduceFunc,
			Predicate<? super T> isMinimal) {
		return reduce(iterable.iterator(), reduceFunc, isMinimal);
	}
	
	public static <T> T reduce(Iterable<? extends T> iterable,
			BiFunction<? super T, ? super T, ? extends T> reduceFunc,
			Predicate<? super T> isMinimal, Supplier<? extends T> def) {
		return reduce(iterable.iterator(), reduceFunc, isMinimal, def);
	}
	
	public static <T> T reduce(Iterable<? extends T> iterable,
			BiFunction<? super T, ? super T, ? extends T> reduceFunc, Supplier<? extends T> def) {
		return reduce(iterable.iterator(), reduceFunc, def);
	}
	
	public static <T> T reduce(Iterator<? extends T> iter,
			BiFunction<? super T, ? super T, ? extends T> reduceFunc) {
		if(!iter.hasNext())
			throw new IllegalArgumentException(iter + " is empty");
		var e = iter.next();
		while(iter.hasNext())
			e = reduceFunc.apply(e, iter.next());
		return e;
	}
	
	public static <T> T reduce(Iterator<? extends T> iter,
			BiFunction<? super T, ? super T, ? extends T> reduceFunc,
			Predicate<? super T> isMinimal) {
		if(!iter.hasNext())
			throw new IllegalArgumentException(iter + " is empty");
		var e = iter.next();
		while(iter.hasNext()) {
			if(isMinimal.test(e))
				return e;
			e = reduceFunc.apply(e, iter.next());
		}
		return e;
	}
	
	public static <T> T reduce(Iterator<? extends T> iter,
			BiFunction<? super T, ? super T, ? extends T> reduceFunc,
			Predicate<? super T> isMinimal, Supplier<? extends T> def) {
		if(!iter.hasNext())
			return def.get();
		return reduce(iter, reduceFunc, isMinimal);
	}
	
	public static <T> T reduce(Iterator<? extends T> iter,
			BiFunction<? super T, ? super T, ? extends T> reduceFunc, Supplier<? extends T> def) {
		if(!iter.hasNext())
			return def.get();
		return reduce(iter, reduceFunc);
	}
	
	public static <T> Iterable<T> removeBack(Iterable<T> iterable) {
		return new RemoveBackIterable<>(iterable);
	}
	
	public static <T> Iterator<T> removeBack(Iterator<T> iterator) {
		return new RemoveBackIterator<>(iterator);
	}
	
	public static int size(Collection<?> ts) {
		return ts.size();
	}
	
	public static int size(Iterable<?> iterable) {
		if(iterable instanceof SizedIterable)
			return size((SizedIterable<?>) iterable);
		if(iterable instanceof Collection)
			return size((Collection<?>) iterable);
		return size(iterator(iterable));
	}
	
	public static int size(Iterator<?> iterator) {
		if(iterator instanceof SizedIterator)
			return size((SizedIterator<?>) iterator);
		int s = 0;
		while(iterator.hasNext()) {
			iterator.next();
			s++;
		}
		return s;
	}
	
	public static int size(SizedIterable<?> iterable) {
		return iterable.size();
	}
	
	public static int size(SizedIterator<?> iterator) {
		return iterator.size();
	}
	
	@SafeVarargs
	public static <T> int size(T... ts) {
		return ts.length;
	}
	
	public static <T> Collection<T> toCollection(Iterable<T> iterable) {
		if(iterable instanceof Collection)
			return (Collection<T>) iterable;
		return toCollection(iterable.iterator());
	}
	
	public static <T> Collection<T> toCollection(Iterator<T> iterator) {
		Collection<T> col = new ArrayList<>();
		for(var t : foreach(iterator))
			col.add(t);
		return col;
	}
	
	@SafeVarargs
	public static <T> Collection<T> toCollection(T... ts) {
		return toCollection(iterator(ts));
	}
	
	public static String toString(Iterable<?> iterable) {
		return toCollection(iterable).toString();
	}
	
	public static <T> Supplier<Supplier<T>> toSuppliers(Iterable<T> iter) {
		return toSuppliers(iter.iterator());
	}
	
	public static <T> Supplier<Supplier<T>> toSuppliers(Iterator<T> iter) {
		return ()-> {
			if(!iter.hasNext())
				return null;
			return ()->iter.next();
		};
	}
	
	public static <T> Iterable<T> union(Iterable<? extends Iterable<? extends T>> ts) {
		return new MergeIterable<>(ts);
	}
	
	@SafeVarargs
	public static <T> Iterable<T> union(Iterable<? extends T>... ts) {
		return union(iterable(ts));
	}
	
	@SafeVarargs
	public static <T> Iterator<T> union(Iterator<? extends T>... ts) {
		return new MergeIterator<>(ts);
	}
	
}
