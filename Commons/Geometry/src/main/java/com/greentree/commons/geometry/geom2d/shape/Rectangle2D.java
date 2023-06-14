package com.greentree.commons.geometry.geom2d.shape;

import com.greentree.commons.geometry.geom2d.util.Transform2DImpl;
import com.greentree.commons.math.vector.Vector2f;

public final class Rectangle2D extends Shape2D {

    private final static Vector2f[] POINTS = new Vector2f[]{new Vector2f(-1, -1),
            new Vector2f(1, -1), new Vector2f(1, 1), new Vector2f(-1, 1),};

    public Rectangle2D(float width, float height) {
        this();
        scale(width, height);
    }

    public Rectangle2D() {
        super(new Transform2DImpl(), POINTS);
    }

    @Override
    public Rectangle2D clone() {
        throw new UnsupportedOperationException();
    }

}
