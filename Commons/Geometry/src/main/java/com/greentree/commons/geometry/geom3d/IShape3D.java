package com.greentree.commons.geometry.geom3d;

import com.greentree.commons.geometry.geom2d.Shape2DUtil;
import com.greentree.commons.geometry.geom3d.face.Face;
import com.greentree.commons.geometry.geom3d.operation.Shape3DBinaryOperations;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.Vector3fKt;
import org.joml.Matrix4f;
import org.joml.Vector2fc;
import org.joml.Vector3fc;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public interface IShape3D {

    default float distanse(Vector3fc p) {
        return Mathf.sqrt(distanceSquared(p));
    }

    default float distanceSquared(Vector3fc p) {
        return minPoint(p).distanceSquared(p);
    }

    default Vector3fc minPoint(final Vector3fc point) {
        Vector3fc res = null, pi;
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

    default Vector3fc[] getPoints() {
        Set<Vector3fc> points = new HashSet<>();
        for (var f : getFaces()) {
            points.add(f.getP1());
            points.add(f.getP2());
            points.add(f.getP3());
        }
        var array = new Vector3fc[points.size()];
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

    default Vector3fc getCenter() {
        return Shape3DUtil.getCenter(getPoints());
    }

    default Vector3fc getCenter(Matrix4f model) {
        return Shape3DUtil.getCenter(Shape3DUtil.getPoints(getPoints(), model));
    }

    default int getFacesCount() {
        return getFaces().length;
    }

    default Vector3fc[] getNormals() {
        var faces = getFaces();
        Collection<Vector3fc> normals = new HashSet<>(faces.length);
        for (var f : faces)
            normals.add(f.getNormal());
        var array = new Vector3fc[normals.size()];
        return normals.toArray(array);
    }

    default Vector2fc[] getProjection(final Vector3fc normal) {
        var points3 = getPoints();
        Set<Vector2fc> points2 = new HashSet<>(points3.length);
        for (var element : points3)
            points2.add(Vector3fKt.projection(element, normal));
        var array = new Vector2fc[points2.size()];
        return Shape2DUtil.getMinimalConvexHullGraham(points2.toArray(array));
    }

    default float getRadius() {
        return getRadius(getCenter());
    }

    default float getRadius(Vector3fc center) {
        return Shape3DUtil.getRadius(center, getPoints());
    }

    default float getRadius(Vector3fc center, Matrix4f model) {
        var p = Shape3DUtil.getPoints(getPoints(), model);
        return Shape3DUtil.getRadius(center, p);
    }

    default float getRadius(Matrix4f model) {
        var p = Shape3DUtil.getPoints(getPoints(), model);
        return Shape3DUtil.getRadius(Shape3DUtil.getCenter(p), p);
    }

    default boolean isInside(Vector3fc p) {
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
