package com.greentree.commons.geometry.geom2d.shape;

import static com.greentree.commons.geometry.geom2d.util.VectorGeometryUtil.*;

import org.joml.Matrix2f;

import com.greentree.commons.geometry.geom2d.util.Transform2DImpl;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.Vector2f;

public final class Capsule2D extends Shape2D {
	
	public Capsule2D(AbstractVector2f p1, AbstractVector2f p2, float radius) {
		super(new Transform2DImpl(), points(p1, p2, radius));
	}
	
	public static Vector2f[] points(AbstractVector2f focus1, AbstractVector2f focus2,
			float radius) {
		final AbstractVector2f focus_vec = focus2.sub(focus1, new Vector2f());// 1 -> 2
		if(focus_vec.length() == 0)
			focus_vec.set(1, 0);
		focus_vec.mul(new Matrix2f().rotate((float) (Math.PI / 2))).normalize(radius);
		final Vector2f[] points = new Vector2f[POINT_IN_CIRCLE];
		final Matrix2f mat = new Matrix2f().rotate((float) (2 * Math.PI / POINT_IN_CIRCLE));
		for(int i = (int) Math.floor(POINT_IN_CIRCLE / 2f); i < POINT_IN_CIRCLE; i++) {
			focus_vec.mul(mat);
			points[i] = focus1.add(focus_vec, new Vector2f());
		}
		for(int i = 0; i < Math.floor(POINT_IN_CIRCLE / 2f); i++) {
			focus_vec.mul(mat);
			points[i] = focus2.add(focus_vec, new Vector2f());
		}
		return points;
	}
	
	@Override
	public int getPointsCount() {
		return POINT_IN_CIRCLE;
	}
	
}
