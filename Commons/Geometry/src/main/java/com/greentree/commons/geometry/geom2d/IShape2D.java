package com.greentree.commons.geometry.geom2d;

import com.greentree.commons.geometry.geom2d.shape.Triangle2D;
import com.greentree.commons.geometry.geom2d.util.VectorGeometryUtil;
import com.greentree.commons.geometry.math.MathLine1D;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.Vector2f;

import java.util.ArrayList;
import java.util.Collection;

public interface IShape2D {

    default ILine2D minLine(AbstractVector2f point) {
        return Mathf.minElement(l -> l.distanseSqr(point), getLinesLoop());
    }

    default float distanse(AbstractVector2f p) {
        return Mathf.sqrt(distanseSqr(p));
    }

    default float distanseSqr(final AbstractVector2f p) {
        return p.distanceSqr(minPoint(p));
    }

    default AbstractVector2f minPoint(final AbstractVector2f point) {
        AbstractVector2f res = null, mp;
        float dis, dis0 = Float.MAX_VALUE;
        for (final var line : getLinesLoop()) {
            mp = line.minPoint(point);
            dis = mp.distanceSqr(point);
            if (dis < dis0) {
                dis0 = dis;
                res = mp;
            }
        }
        return res;
    }

    default ILine2D[] getLinesLoop() {
        return Shape2DUtil.toLineLoop(getPoints());
    }

    AbstractVector2f[] getPoints();

    default float getArea() {
        float res = 0;
        for (var tr : VectorGeometryUtil.triangulation(this))
            res += tr.getArea();
        return res;
    }

    default ILine2D[] getLinesStrip() {
        return Shape2DUtil.toLineStrip(getPoints());
    }

    default Collection<AbstractVector2f> getNormals() {
        Collection<AbstractVector2f> res = new ArrayList<>();
        for (var l : getLinesLoop())
            res.add(l.getNormal());
        return res;
    }

    default float getPerimeter() {
        float res = 0;
        for (var line : getLinesLoop())
            res += line.getPerimeter();
        return res;
    }

    default int getPointsCount() {
        return getPoints().length;
    }

    default MathLine1D getProjection(AbstractVector2f normal) {
        return Shape2DUtil.getProjection(getPoints(), normal);
    }

    default MathLine1D getProjectionX() {
        return Shape2DUtil.getProjectionX(getPoints());
    }

    default MathLine1D getProjectionY() {
        return Shape2DUtil.getProjectionY(getPoints());
    }

    default float getRadius() {
        final var c = getCenter();
        return Mathf.max(p -> c.distanceSqr(p), getPoints());
    }

    default AbstractVector2f getCenter() {
        return VectorGeometryUtil.getCenter(getPoints());
    }

    default boolean isInside(float x, float y) {
        return isInside(new Vector2f(x, y));
    }

    default boolean isInside(AbstractVector2f p) {
        if (!getAABB().isIntersect(p.x(), p.y()))
            return false;
        var points = VectorGeometryUtil.cycle(getPoints(), 1);
        for (int i = 0; i < points.length - 1; i++)
            if (0 < VectorGeometryUtil.getSin(points[i], points[i + 1], p))
                return false;
        return true;
    }

    default AABB getAABB() {
        return new AABB(this);
    }

    default boolean isIntersect(IShape2D other) {
        return Shape2DUtil.isIntersect(this, other);
    }

    default Collection<Triangle2D> triangulation() {
        return VectorGeometryUtil.triangulation(getPoints());
    }

}
