package com.greentree.commons.geometry.geom2d;

import com.greentree.commons.math.vector.AbstractVector2f;

public interface IMovableShape2D extends IShape2D {

    default void add(AbstractVector2f v) {
        add(v.x(), v.y());
    }

    void add(float x, float y);

    default void moveTo(final AbstractVector2f p) {
        this.moveTo(p.x(), p.y());
    }

    default void moveTo(final float x, final float y) {
        final AbstractVector2f p = getCenter();
        add(x - p.x(), y - p.y());
    }

    default void rotate(double angle) {
        rotate(getCenter(), angle);
    }

    void rotate(AbstractVector2f point, double angle);

    default void scale(AbstractVector2f v) {
        scale(v.x(), v.y());
    }

    void scale(float x, float y);

    default void scale(float xy) {
        scale(xy, xy);
    }

    default void setSize(final float width, final float height) {
        final var aabb = new AABB(getPoints());
        scale(width / aabb.getWidth(), height / aabb.getHeight());
    }

}
