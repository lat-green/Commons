package com.greentree.commons.math;

import org.joml.Vector3f;
import org.joml.Vector3fc;

public class MathPlane3D {

    public final float a, b, c, d;

    public MathPlane3D(Vector3fc point, Vector3fc normal) {
        a = normal.x();
        b = normal.y();
        c = normal.z();
        if (normal.lengthSquared() < Mathf.EPS)
            throw new IllegalArgumentException(String.format("is not vector (%f, %f, %f)", a, b, c));
        d = -(a * point.x() + b * point.y() + c * point.z());
    }

    public MathPlane3D(Vector3fc p0, Vector3fc p1, Vector3fc p2) {
        float x1 = p1.x() - p0.x();
        float y1 = p1.y() - p0.y();
        float z1 = p1.z() - p0.z();
        float x2 = p2.x() - p0.x();
        float y2 = p2.y() - p0.y();
        float z2 = p2.z() - p0.z();
        a = y1 * z2 - y2 * z1;
        b = x1 * z2 - x2 * z1;
        c = x1 * y2 - x2 * y1;
        if (a * a + b * b + c * c < Mathf.EPS)
            throw new IllegalArgumentException(String.format("is not vector (%f, %f, %f)", a, b, c));
        d = -(a * p0.x() + b * p0.y() + c * p0.z());
    }

    public MathPlane3D(float a, float b, float c, float d) {
        if (a * a + b * b + c * c < Mathf.EPS)
            throw new IllegalArgumentException(String.format("is not vector (%f, %f, %f)", a, b, c));
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public MathLine3D contact(MathPlane3D other) {
        float det = b * other.c - c * other.b;
        var vec = getNormal().cross(other.getNormal());
        if (det != 0) {
            float d1 = c * other.d - d * other.c;
            float d2 = b * other.d - d * other.b;
            return new MathLine3D(new Vector3f(0, d1 / det, d2 / det), vec);
        }
        det = a * other.c - c * other.a;
        if (det != 0) {
            float d1 = a * other.d - d * other.a;
            float d2 = c * other.d - d * other.c;
            return new MathLine3D(new Vector3f(d1 / det, 0, d2 / det), vec);
        }
        det = a * other.b - b * other.a;
        float d1 = b * other.d - d * other.b;
        float d2 = a * other.d - d * other.a;
        return new MathLine3D(new Vector3f(d1 / det, d2 / det, 0), vec);
    }

    public Vector3f getNormal() {
        return new Vector3f(a, b, c);
    }

    public float distanse(Vector3fc point) {
        return Mathf.abs(a * point.x() + b * point.y() + c * point.z() + d) / (a * a + b * b + c * c);
    }

    public float get(Vector3fc point) {
        return a * point.x() + b * point.y() + c * point.z() + d;
    }

    public float getA() {
        return a;
    }

    public float getB() {
        return b;
    }

    public float getC() {
        return c;
    }

    public float getD() {
        return d;
    }

    public MathLine2D getXY(float z0) {
        return new MathLine2D(a, b, z0 * c + d);
    }

    public MathLine2D getXZ(float y0) {
        return new MathLine2D(a, c, y0 * b + d);
    }

    public MathLine2D getYZ(float x0) {
        return new MathLine2D(b, c, x0 * a + d);
    }

    public boolean isCoplanar(Vector3fc vector) {
        return Mathf.equals(0, vector.dot(getNormal()));
    }

    public Vector3fc minPoint(Vector3fc point) {
        float t = -(a * point.x() + b * point.y() + c * point.z() + d) / (a * a + b * b + c * c);
        return new Vector3f(point.x() + a * t, point.y() + b * t, point.z() + c * t);
    }

    @Override
    public String toString() {
        return "MathPlane3D [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + "]";
    }

}
