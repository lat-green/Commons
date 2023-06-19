package com.greentree.commons.geometry.geom2d.operation;

import com.greentree.commons.geometry.geom2d.collision.CollisionEvent2D;
import com.greentree.commons.geometry.geom2d.shape.Ellipse2D;
import com.greentree.commons.math.vector.Vector2f;

public class TEllipsevsTEllipse extends Shape2DBinaryOperation<Ellipse2D, Ellipse2D> {

    @Override
    public CollisionEvent2D.Builder getCollisionEvent(Ellipse2D a, Ellipse2D b) {
        var n = b.getCenter().minus(a.getCenter());
        var p = -n.length() + a.getRadius() + b.getRadius();
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
                n.normalize(1), p);
        return res;
    }

    @Override
    public boolean isIntersect(final Ellipse2D a, final Ellipse2D b) {
        float r = a.getRadius() + b.getRadius();
        return b.getCenter().distanceSquared(a.getCenter()) <= r * r;
    }

}
