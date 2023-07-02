package com.greentree.commons.geometry.geom2d.operation;

import com.greentree.commons.geometry.geom2d.IShape2D;
import com.greentree.commons.geometry.geom2d.Shape2DUtil;
import com.greentree.commons.geometry.geom2d.collision.CollisionEvent2D;
import com.greentree.commons.math.MathLine2D;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.Vector2f;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Shape2DBinaryOperation<A extends IShape2D, B extends IShape2D> {

    public static final Shape2DBinaryOperation DEFAULT = new Shape2DBinaryOperation();

    public CollisionEvent2D.Builder getCollisionEvent(A a, B b) {
        var normal = Shape2DUtil.getCollisionNormalOnNormalProjection(a, b);
        return new CollisionEvent2D.Builder(Shape2DUtil.getCollisionPoint(a, b), normal,
                Shape2DUtil.getProjectionOverlay(a, b, new Vector2f(normal.y(), -normal.x())));
    }

    public Collection<AbstractVector2f> getContactPoint(final A a, final B b) {
        final Set<AbstractVector2f> res = new HashSet<>();
        for (final var al : a.getLinesLoop())
            for (final var bl : b.getLinesLoop()) {
                final Vector2f c = MathLine2D.contact(al.getMathLine(), bl.getMathLine());
                if (c == null || !al.getAABB().isInside(c.x(), c.y()) || !bl.getAABB().isInside(c.x(), c.y()))
                    continue;
                res.add(c);
            }
        return res;
    }

    public boolean isIntersect(final A a, final B b) {
        if (!a.getAABB().isIntersect(b.getAABB()))
            return false;
        return Shape2DUtil.isIntersectOnNormalProjection(a, b);
    }

}
