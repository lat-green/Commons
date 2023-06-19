package com.greentree.commons.geometry.geom3d;

import com.greentree.commons.geometry.geom2d.Shape2DUtil;
import com.greentree.commons.geometry.geom3d.face.Face;
import com.greentree.commons.geometry.geom3d.operation.Shape3DBinaryOperations;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.AbstractVector3f;
import org.joml.Matrix4f;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public interface IShape3D {

    default float distanse(AbstractVector3f p) {
        return Mathf.sqrt(distanceSquared(p));
    }

    default float distanceSquared(AbstractVector3f p) {
        return minPoint(p).distanceSquared(p);
    }

    default AbstractVector3f minPoint(final AbstractVector3f point) {
        AbstractVector3f res = null, pi;
        float dis, dis0 = Float.MAX_VALUE;
        for (final var f : getFaces()) {
            pi = f.minPoint(point);
            dis = pi.distanceSquared(point);
            if (dis < dis0) {
                dis0 = dis;
                res = pi;
            }
        }
        return res;
    }

    Face[] getFaces();

    default AABB getAABB() {
        return new AABB(getPoints());
    }

    default AbstractVector3f[] getPoints() {
        Set<AbstractVector3f> points = new HashSet<>();
        for (var f : getFaces()) {
            points.add(f.getP1());
            points.add(f.getP2());
            points.add(f.getP3());
        }
        var array = new AbstractVector3f[points.size()];
        return points.toArray(array);
    }

    default AABB getAABB(Matrix4f model) {
        return new AABB(Shape3DUtil.getPoints(getPoints(), model));
    }

    default float getArea() {
        float res = 0;
        for (final var f : getFaces())
            res += f.getArea();
        return res;
    }

    default AbstractVector3f getCenter() {
        return Shape3DUtil.getCenter(getPoints());
    }

    default AbstractVector3f getCenter(Matrix4f model) {
        return Shape3DUtil.getCenter(Shape3DUtil.getPoints(getPoints(), model));
    }

    default int getFacesCount() {
        return getFaces().length;
    }

    default AbstractVector3f[] getNormals() {
        var faces = getFaces();
        Collection<AbstractVector3f> normals = new HashSet<>(faces.length);
        for (var f : faces)
            normals.add(f.getNormal());
        var array = new AbstractVector3f[normals.size()];
        return normals.toArray(array);
    }

    default AbstractVector2f[] getProjection(final AbstractVector3f normal) {
        var points3 = getPoints();
        Set<AbstractVector2f> points2 = new HashSet<>(points3.length);
        for (var element : points3)
            points2.add(element.getProjection(normal));
        var array = new AbstractVector2f[points2.size()];
        return Shape2DUtil.getMinimalConvexHullGraham(points2.toArray(array));
    }

    default float getRadius() {
        return getRadius(getCenter());
    }

    default float getRadius(AbstractVector3f center) {
        return Shape3DUtil.getRadius(center, getPoints());
    }

    default float getRadius(AbstractVector3f center, Matrix4f model) {
        var p = Shape3DUtil.getPoints(getPoints(), model);
        return Shape3DUtil.getRadius(center, p);
    }

    default float getRadius(Matrix4f model) {
        var p = Shape3DUtil.getPoints(getPoints(), model);
        return Shape3DUtil.getRadius(Shape3DUtil.getCenter(p), p);
    }

    default boolean isInside(AbstractVector3f p) {
        for (final Face f : getFaces()) {
            var mp = f.getMathPlane();
            //			System.out.println(mp + " " + mp.get(p));
            if (mp.get(p) > 0)
                return false;
        }
        return true;
    }

    default boolean isIntersect(IShape3D other) {
        return Shape3DBinaryOperations.isIntersect(this, other);
    }

}
