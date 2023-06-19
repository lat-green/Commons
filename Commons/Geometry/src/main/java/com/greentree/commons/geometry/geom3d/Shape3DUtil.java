package com.greentree.commons.geometry.geom3d;

import com.greentree.commons.geometry.geom2d.shape.Poligon2D;
import com.greentree.commons.geometry.geom3d.operation.Shape3DBinaryOperations;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.AbstractVector3f;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public final class Shape3DUtil extends Shape3DBinaryOperations {

    public static AbstractVector3f[] getPoints(AbstractVector3f[] points, Matrix4f model) {
        var result = new AbstractVector3f[points.length];
        for (int i = 0; i < points.length; i++) {
            result[i] = points[i].times(model);
        }
        return result;
    }

    public static float getProjectionArea(IShape3D shape, AbstractVector3f normal) {
        return getProjectionPoligon(shape, normal).getArea();
    }

    public static Poligon2D getProjectionPoligon(IShape3D shape, AbstractVector3f normal) {
        return new Poligon2D(shape.getProjection(normal));
    }

    public static float getRadius(AbstractVector3f center, AbstractVector3f[] points) {
        return Mathf.sqrt(Mathf.max(p -> p.distanceSquared(center), points));
    }

    /** @return sin of ABC angle */
    public static float getSin(AbstractVector3f a, AbstractVector3f b, AbstractVector3f c) {
        var v1 = a.minus(b);
        var v2 = c.minus(b);
        var cr = v1.cross(v2);
        float len = cr.length() / v1.length() / v2.length();
        if (isRight(a, b, c))
            return -len;
        return len;
    }

    public static boolean isRight(AbstractVector3f a, AbstractVector3f b, AbstractVector3f c) {
        return new Matrix3f(a.x(), a.y(), a.z(), b.x(), b.y(), b.z(), c.x(), c.y(), c.z())
                .determinant() > 0;
    }

    public static <A extends IShape3D, B extends IShape3D> boolean isIntersectOnNormalProjection(
            A a, B b) {
        for (var an : a.getNormals())
            if (!new Poligon2D(a.getProjection(an)).isIntersect(new Poligon2D(b.getProjection(an))))
                return false;
        return true;
    }
    //	public static AbstractVector3f getCollisionNormalOnNormalProjection(IShape3D a, IShape3D b) {
    //		final AbstractVector3f n1 = getCollisionNormalOnNormalProjection0(a, b);
    //		final AbstractVector3f n2 = getCollisionNormalOnNormalProjection0(b, a).mul(-1);
    //		final float o1 = getProjectionOverlay(a, b, n1);
    //		final float o2 = getProjectionOverlay(a, b, n2);
    //		if(o1 < o2)
    //			return n1;
    //		else
    //			return n2;
    //	}
    //
    //	public static float getProjectionOverlay(IShape3D a, IShape3D b, AbstractVector3f normal) {
    //		var s1 = getProjectionPoligon(a, normal);
    //		var s2 = getProjectionPoligon(b, normal);
    //		return Shape2DUtil.getProjectionOverlay(s1, s2);
    //	}
    //	public static AbstractVector3f getCollisionNormalOnNormalProjection0(IShape3D a, IShape3D b) {
    //		AbstractVector3f[] normals;
    //		var rv = b.getCenter().sub(a.getCenter(), new Vector3f()).normalize();
    //		{
    //			Collection<AbstractVector3f> normals0 = new ArrayList<>();
    //			Collections.addAll(normals0, a.getNormals());
    //			if(rv.lengthSquared() > 0)
    //				normals0 = normals0.parallelStream().filter(n -> n.dot(rv) > 0).collect(Collectors.toList());
    //			normals = new Vector3f[normals0.size()];
    //			normals0.toArray(normals);
    //		}
    //		AbstractVector3f res_normal = normals[0];
    //		{
    //			float res_overlay = getProjectionOverlay(a, b, res_normal);
    //			for(int i = 1; i < normals.length; i++) {
    //				final var normal = normals[i];
    //				final float overlay = getProjectionOverlay(a, b, normal);
    //				if(overlay > res_overlay) {
    //					res_normal = normal;
    //					res_overlay = overlay;
    //				}
    //			}
    //		}
    //		return res_normal;
    //	}

}
