package com.greentree.commons.util.iterator;

import java.util.Iterator;
import java.util.function.Function;


public class FuncMapIterator<T, R> extends MapIterator<T, R> {
	private static final long serialVersionUID = 1L;

	private final Function<? super T, ? extends R> func;

	/**
	 * Creates iterator that applies function to elements.
	 *
	 * @param iter source iterator
	 * @param func mapping function
	 */
	public FuncMapIterator(Iterator<? extends T> iter, Function<? super T, ? extends R> func) {
		super(iter);
		this.func = func;
	}

	/**
	 * Applies function to element.
	 *
	 * @param t element to map
	 * @return mapped element
	 */
	@Override
	protected R func(T t) {
		return func.apply(t);
	}
}
