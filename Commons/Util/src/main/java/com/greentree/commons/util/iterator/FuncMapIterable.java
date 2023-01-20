package com.greentree.commons.util.iterator;

import java.util.Objects;
import java.util.function.Function;

public final class FuncMapIterable<T, R> extends MapIterable<T, R> {
	private static final long serialVersionUID = 1L;

	private final Function<? super T, ? extends R> func;

	public FuncMapIterable(Iterable<? extends T> iter, Function<? super T, ? extends R> func) {
		super(iter);
		this.func = func;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		FuncMapIterable<?, ?> other = (FuncMapIterable<?, ?>) obj;
		return Objects.equals(func, other.func) && super.equals(obj);
	}

	@Override
	public int hashCode() {
		return Objects.hash(func) + 31 * super.hashCode();
	}

	@Override
	protected R func(T t) {
		return func.apply(t);
	}

}
