package com.greentree.commons.geometry.geom2d.shape;

import com.greentree.commons.geometry.geom2d.util.Transform2DImpl;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.Vector2f;

public final class Triangle2D extends Shape2D {

    public Triangle2D(AbstractVector2f v1, AbstractVector2f v2, AbstractVector2f v3) {
        super(new Transform2DImpl(), points(v1, v2, v3));
    }

    private static AbstractVector2f[] points(AbstractVector2f v1, AbstractVector2f v2,
                                             AbstractVector2f v3) {
        return new AbstractVector2f[]{v1, v2, v3};
    }

    public Triangle2D(float x1, float y1, float x2, float y2, float x3, float y3) {
        super(new Transform2DImpl(), points(x1, y1, x2, y2, x3, y3));
    }

    private static AbstractVector2f[] points(float x1, float y1, float x2, float y2, float x3,
                                             float y3) {
        return points(vec(x1, y1), vec(x2, y2), vec(x3, y3));
    }

    private static AbstractVector2f vec(float x, float y) {
        return new Vector2f(x, y);
    }

    @Override
    public Triangle2D clone() {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getArea() {
        var v1 = p2().minus(p1());
        var v2 = p3().minus(p1());
        return Mathf.abs(v1.cross(v2)) / 2f;
    }

    public AbstractVector2f p2() {
        return getPoints()[1];
    }

    public AbstractVector2f p1() {
        return getPoints()[0];
    }

    public AbstractVector2f p3() {
        return getPoints()[2];
    }

}
