package com.greentree.commons.geometry.geom2d;

import com.greentree.commons.geometry.geom2d.collision.Collidable2D;

import java.util.Objects;

public final class WrapCollidable implements Collidable2D {

    private final Shape2D shape;

    public WrapCollidable(Shape2D shape) {
        this.shape = shape;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shape);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        WrapCollidable other = (WrapCollidable) obj;
        return Objects.equals(shape, other.shape);
    }

    @Override
    public String toString() {
        return "WrapCollidable [" + shape + "]";
    }

    @Override
    public Shape2D getShape() {
        return shape;
    }

}