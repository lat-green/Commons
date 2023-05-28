package com.greentree.commons.geometry.math;

import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.Vector2f;

public class MathLine2D {


	public final float a, b, c;

	public MathLine2D(AbstractVector2f normal) {
		this(normal.x(), normal.y(), 0);
	}

	public MathLine2D(AbstractVector2f p1, AbstractVector2f p2) {

		//		(x-x1)/(x2-x1)=(y-y1)/(y2-y1)
		//		(y2-y1)(x-x1)=(y-y1)(x2-x1)
		//		(y2-y1)(x-x1)+(y-y1)(x1-x2)=0
		//		(y2-y1)x + (x1-x2)y + ((-x1)(y2-y1) + (-y1)(x1-x2)) = 0
		//		(a)x + (b)y + ((-x1)(b) + (-y1)(a)) = 0

		a = p2.y() - p1.y();
		b = p1.x() - p2.x();
		if(a*a + b*b < Mathf.EPS)throw new IllegalArgumentException(String.format("is not vector (%f, %f)", a, b));
		c = -p1.x() * a - p1.y() * b;
	}

	public MathLine2D(float a, float b, float c) {
		if(a*a + b*b < Mathf.EPS)throw new IllegalArgumentException(String.format("is not vector (%f, %f)", a, b));
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public static Vector2f contact(MathLine2D l1, MathLine2D l2) {
		final float det = l1.a * l2.b - l2.a * l1.b;
		if(det == 0) return null;

		//		final float x = -(a2 * b.getX1() * b1 + b2 * b.getY1() * b1 - (a1 * a.getX1() + b1 * a.getY1()) * b2) / z;
		//		final float y = (a2 * b.getX1() * a1 + b2 * b.getY1() * a1 - (a1 * a.getX1() + b1 * a.getY1()) * a2) / z;

		final float x = -(l2.b * l1.c - l1.b * l2.c) / det;
		final float y = (l2.a * l1.c - l1.a * l2.c) / det;
		//		System.out.printf("%.2f %.2f\n%.2f %.2f\n\n", x - l1.getX(y), y - l1.getY(x), x - l2.getX(y), y - l2.getY(x));
		return new Vector2f(x, y);
	}

	public static MathLine2D createPointAndNormal(AbstractVector2f point, AbstractVector2f normal) {
		return new MathLine2D(normal.x(), normal.y(), -point.x() * normal.x() - point.y() * normal.y());
	}

	public float getA() {
		return a;
	}

	public float getB() {
		return b;
	}



	public float getC() {
		return c;
	}

	public Vector2f getNormal() {
		return new Vector2f(a, b).normalize();
	}

	public MathLine2D getPerpendicular() {
		return new MathLine2D(-b, a, 0);
	}


	public MathLine2D getPerpendicular(float x, float y) {
		return new MathLine2D(-b, a, x * -b - y * a);
	}

	public float getX(float y) {
		return -(b*y + c) / a;
	}

	public float getY(float x) {
		return -(a*x + c) / b;
	}
	public Vector2f minPoint(AbstractVector2f p) {
		float a2,b2,c2;

		a2 = -b;
		b2 = a;
		c2 = -a2 * p.x() - b2 * p.y();

		float det = a * b2 - a2 * b;

		return new Vector2f(-(b2 * c + a2 * c2) / det, -(b * c + a * c2) / det);
	}

	protected Vector2f minPoint0(AbstractVector2f p) {
		float a2,b2,c2;

		a2 = -b;
		b2 = a;
		c2 = -a2 * p.x() - b2 * p.y();

		float det = a * b2 - a2 * b;

		final float x = -(b2 * c + a2 * c2) / det;
		final float y = -(b * c + a * c2) / det;

		return new Vector2f(x, y);
	}

	public MathLine2D moveTo(float x, float y) {
		return new MathLine2D(a, b,x * a - y * b);
	}

	@Override
	public String toString() {
		return "MathLine2D [a=" + a + ", b=" + b + ", c=" + c + "]";
	}



}
