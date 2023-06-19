package com.greentree.commons.geometry.geom2d.operation;

import com.greentree.commons.geometry.geom2d.collision.CollisionEvent2D;
import com.greentree.commons.geometry.geom2d.shape.Circle2D;
import com.greentree.commons.math.vector.Vector2f;

public class CirclevsCircle extends Shape2DBinaryOperation<Circle2D, Circle2D> {

    @Override
    public CollisionEvent2D.Builder getCollisionEvent(Circle2D a, Circle2D b) {
        final var n = b.getCenter().minus(a.getCenter());
        final var penetration = -n.length() + a.getRadius() + b.getRadius();
        float x1 = a.getCenter().x();
        float y1 = a.getCenter().y();
        float r1 = a.getRadius();
        float x2 = b.getCenter().x() - x1;
        float y2 = b.getCenter().y() - y1;
        float r2 = b.getRadius();
        float al = -2 * x2;
        float bl = -2 * y2;
        float cl = x2 * x2 + y2 * y2 + r1 * r1 - r2 * r2;
        float ab = al * al + bl * bl;
        var res = new CollisionEvent2D.Builder(new Vector2f(-al * cl / ab + x1, -bl * cl / ab + y1),
                n.normalize(1), penetration);
        return res;
    }

    @Override
    public boolean isIntersect(final Circle2D a, final Circle2D b) {
        float r = a.getRadius() + b.getRadius();
        return b.getCenter().distanceSqr(a.getCenter()) <= r * r;
    }

}
