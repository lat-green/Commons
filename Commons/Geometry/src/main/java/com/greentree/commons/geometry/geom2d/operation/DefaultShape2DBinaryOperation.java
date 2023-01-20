package com.greentree.commons.geometry.geom2d.operation;

import com.greentree.commons.geometry.geom2d.IShape2D;

public final class DefaultShape2DBinaryOperation<A extends IShape2D, B extends IShape2D> extends Shape2DBinaryOperation<A, B> {

	@SuppressWarnings("rawtypes")
	public final static Shape2DBinaryOperation DEFAULT = new DefaultShape2DBinaryOperation<>();
	private DefaultShape2DBinaryOperation() {
	}

}
