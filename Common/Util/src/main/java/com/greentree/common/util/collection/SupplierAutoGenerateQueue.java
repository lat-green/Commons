package com.greentree.common.util.collection;

import java.util.Collection;
import java.util.function.Supplier;

public class SupplierAutoGenerateQueue<E> extends AutoGenerateQueue<E> {
	private static final long serialVersionUID = 1L;

	private final Supplier<E> supplier;

	public SupplierAutoGenerateQueue(Supplier<E> supplier) {
		this.supplier = supplier;
	}

	public SupplierAutoGenerateQueue(Supplier<E> supplier, Collection<? extends E> c) {
		super(c);
		this.supplier = supplier;
	}

	public SupplierAutoGenerateQueue(Supplier<E> supplier, int numElements) {
		super(numElements);
		this.supplier = supplier;
	}

	@Override
	protected E generate() {
		return supplier.get();
	}

}
