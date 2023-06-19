package com.greentree.commons.geometry.geom2d.shape;

import com.greentree.commons.geometry.geom2d.AABB;
import com.greentree.commons.geometry.geom2d.util.VectorGeometryUtil;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.AbstractMutableVector2f;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.FinalVector2f;
import com.greentree.commons.math.vector.Vector2f;

public final class Circle2D extends Shape2D {

    private final static FinalVector2f[] POINTS = VectorGeometryUtil.getUnitCirclePoints();

    public Circle2D() {
        this(0, 0);
    }

    public Circle2D(float x, float y) {
        this(x, y, 1);
    }

    public Circle2D(float x, float y, float radius) {
        super(new CircleTransform2D(x, y, radius), POINTS);
    }

    public Circle2D(float radius) {
        this(0, 0, radius);
    }

    @Override
    public Circle2D clone() {
        return new Circle2D(getCenter().x(), getCenter().y(), getRadius());
    }

    @Override
    public String toString() {
        return "Circle2D[" + getRadius() + ", " + getCenter() + "]";
    }

    @Override
    public float getArea() {
        float r = getRadius();
        return Mathf.PI * r * r;
    }

    @Override
    public float getPerimeter() {
        return Mathf.PI2 * getRadius();
    }

    @Override
    public float getRadius() {
        return getTransform().getScale().x();
    }

    @Override
    public boolean isInside(AbstractVector2f p) {
        float d = p.distanceSquared(getCenter());
        float r = getRadius();
        return d <= r * r;
    }

    @Override
    public AABB getAABB() {
        float r = getRadius(), r2 = 2 * r;
        final var c = getCenter();
        return new AABB(c.x() - r, c.x() + r, c.y() - r, c.y() + r);
    }

    public static final class CircleTransform2D implements Transform2D {

        private final AbstractMutableVector2f position;
        private float scale;

        public CircleTransform2D(float x, float y, float r) {
            position = new Vector2f(x, y);
            scale = r;
        }

        @Override
        public float getRotation() {
            return 0;
        }

        @Override
        public AbstractVector2f getScale() {
            return new Vector2f(scale, scale);
        }

        @Override
        public AbstractVector2f getPosition() {
            return position;
        }

        @Override
        public boolean setPosition(float x, float y) {
            position.set(x, y);
            return true;
        }

        @Override
        public boolean setRotation(double rotation) {
            return false;
        }

        @Override
        public boolean setScale(float x, float y) {
            if (Mathf.equals(x, y))
                return setScale(x);
            throw new IllegalArgumentException();
        }

        public boolean setScale(float scale) {
            this.scale = scale;
            return true;
        }

        @Override
        public String toString() {
            return "CircleTransform2D [position=" + position + ", scale=" + scale + "]";
        }

    }

}
