package com.greentree.commons.geometry.geom2d.atomic;

import org.joml.Matrix2f;
import org.joml.Matrix3f;

import com.greentree.commons.geometry.math.MathLine1D;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.Vector2f;
import com.greentree.commons.math.vector.Vector3f;
import com.greentree.commons.util.iterator.IteratorUtil;

public interface SimpleShape2D {
	
	boolean isInsade(AbstractVector2f point);
	AbstractVector2f min(AbstractVector2f point);
	
	MathLine1D getProjection(AbstractVector2f normal);
	
	Iterable<? extends AbstractVector2f> normals();
	Iterable<? extends AbstractVector2f> points();
	
	
	default MathLine1D getProjection(Matrix3f model, AbstractVector2f normal) {
		final var v = new Vector2f(normal).mul(new Matrix2f(model));
		final var len = v.length();
		v.normalize();
		final var ml = getProjection(v);
		return new MathLine1D(ml.min * len, ml.max * len);
	}
	
	default boolean isInsade(Matrix3f model, AbstractVector2f point) {
		var invModelMatrix = new Matrix3f();
		model.invert(invModelMatrix);
		final var v = new Vector3f(point, 1).mul(invModelMatrix);
		return isInsade(v.xy());
	}
	
	default AbstractVector2f min(Matrix3f model, AbstractVector2f point) {
		var invModelMatrix = new Matrix3f();
		model.invert(invModelMatrix);
		final var v = new Vector3f(point, 1).mul(invModelMatrix);
		return new Vector3f(min(v.xy()), 1).mul(model).xy();
	}
	
	default Iterable<? extends AbstractVector2f> normals(Matrix3f model) {
		return IteratorUtil.map(normals(), n->new Vector2f(n).mul(new Matrix2f(model)));
	}
	
	default Iterable<? extends AbstractVector2f> points(Matrix3f model) {
		return IteratorUtil.map(points(), n->new Vector3f(n, 1).mul(model).xy());
	}
	
}
