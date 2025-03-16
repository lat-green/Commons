package com.greentree.commons.geometry.geom3d;

import com.greentree.commons.geometry.geom3d.face.Face;
import org.joml.Vector3fc;

public interface Collidable3D extends IShape3D {

    @Override
    default float distanceSquared(Vector3fc p) {
        return getShape().distanceSquared(p);
    }

    @Override
    default Face[] getFaces() {
        return getShape().getFaces();
    }

    @Override
    default AABB getAABB() {
        return getShape().getAABB();
    }

    @Override
    default float getArea() {
        return getShape().getArea();
    }

    @Override
    default Vector3fc getCenter() {
        return getShape().getCenter();
    }

    @Override
    default boolean isIntersect(IShape3D other) {
        return getShape().isIntersect(other);
    }

    IShape3D getShape();

}
