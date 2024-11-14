package com.greentree.commons.geometry.geom2d.util;

import com.greentree.commons.geometry.geom2d.Shape2D;
import com.greentree.commons.geometry.geom2d.Shape2DUtil;
import com.greentree.commons.geometry.geom2d.shape.FiniteShape2D;
import com.greentree.commons.geometry.geom2d.shape.Line2D;
import com.greentree.commons.geometry.geom2d.shape.Triangle2D;
import com.greentree.commons.geometry.geom3d.IShape3D;
import com.greentree.commons.math.MathLine1D;
import com.greentree.commons.math.MathLine2D;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.*;
import com.greentree.commons.util.iterator.IteratorUtil;
import org.joml.Math;

import java.util.*;
import java.util.stream.Collectors;

import static com.greentree.commons.math.vector.AbstractVector2fKt.vec2f;

public abstract class VectorGeometryUtil {

    public static final int POINT_IN_CIRCLE = 30;

    private static final FinalVector2f[] UNIT_CIRCLE_POINTS = new FinalVector2f[POINT_IN_CIRCLE];

    static {
        final float delta = 2 * Mathf.PI / POINT_IN_CIRCLE;
        for (int i = 0; i < POINT_IN_CIRCLE; i++) {
            float d = Mathf.PI2 + delta * i;
            float sin = Mathf.sin(d);
            float cos = Mathf.cos(d, sin);
            UNIT_CIRCLE_POINTS[i] = new FinalVector2f(cos, sin);
        }
    }

    protected VectorGeometryUtil() {
    }

    public static float areaTriangle(AbstractVector2f a, AbstractVector2f b, AbstractVector2f c) {
        return (b.x() - a.x()) * (c.y() - a.y()) - (b.y() - a.y()) * (c.x() - a.x());
    }

    public static <T extends AbstractVector2f> boolean equals(T[] a, T[] b) {
        if (a.length != b.length)
            return false;
        A:
        for (int s = 0; s < a.length; s++) {
            for (int i = 0; i < a.length; i++)
                if (!a[(i + s) % a.length].equals(b[i]))
                    continue A;
            return true;
        }
        return false;
    }

    /** @return angle of ABC angle */
    public static float getAngle(AbstractVector2f a, AbstractVector2f b, AbstractVector2f c) {
        AbstractVector2f v1 = b.minus(a).normalize(1);
        AbstractVector2f v2 = b.minus(c).normalize(1);
        float sin = v1.cross(v2);
        float cos = v1.dot(v2);
        return Math.atan2(sin, cos);
    }

    public static AbstractVector2f[] getCirclePoints(final float centerx, final float centery,
                                                     final float radius) {
        AbstractVector2f[] vectors = new AbstractVector2f[POINT_IN_CIRCLE];
        for (int i = 0; i < vectors.length; i++) {
            var v = UNIT_CIRCLE_POINTS[i];
            vectors[i] = new Vector2f(centerx + radius * v.x(), centery + radius * v.y());
        }
        return vectors;
    }

    public static AbstractVector2f getCollisionNormalOnNormalProjection(FiniteShape2D a, FiniteShape2D b) {
        final var n1 = getCollisionNormalOnNormalProjection0(a, b);
        final var n2 = getCollisionNormalOnNormalProjection0(b, a).times(-1);
        final float o1 = getProjectionOverlay(a, b, n1);
        final float o2 = getProjectionOverlay(a, b, n2);
        if (o1 < o2)
            return n1;
        else
            return n2;
    }

    public static AbstractVector2f getCollisionNormalOnNormalProjection0(FiniteShape2D a, FiniteShape2D b) {
        Vector2f[] normals;
        var rv = b.getBoundingCircle().getCenter().minus(a.getBoundingCircle().getCenter()).normalize(1);
        {
            Collection<AbstractVector2f> normals0;
            if (rv.lengthSquared() > 0)
                normals0 = a.getNormals().parallelStream().filter(n -> n.dot(rv) > 0)
                        .collect(Collectors.toList());
            else
                normals0 = a.getNormals();
            normals = new Vector2f[normals0.size()];
            normals0.toArray(normals);
        }
        Vector2f res_normal = normals[0];
        {
            float res_overlay = getProjectionOverlay(a, b, res_normal);
            for (int i = 1; i < normals.length; i++) {
                final Vector2f normal = normals[i];
                final float overlay = getProjectionOverlay(a, b, normal);
                if (overlay <= 0)
                    continue;
                if (overlay < res_overlay) {
                    res_normal = normal;
                    res_overlay = overlay;
                }
            }
        }
        res_normal = new Vector2f(res_normal.y(), -res_normal.x());
        if (res_normal.dot(rv) < 0)
            return res_normal.times(-1);
        return res_normal;
    }

    public static float getProjectionOverlay(Shape2D a, Shape2D b, AbstractVector2f normal) {
        return a.projection(normal).getOverlay(b.projection(normal));
    }

    public static AbstractVector2f getCollisionPoint(FiniteShape2D a, FiniteShape2D b) {
        Collection<AbstractVector2f> points = new ArrayList<>();
        for (AbstractVector2f p : a.getPoints())
            if (b.isInside(p))
                points.add(p);
        for (AbstractVector2f p : b.getPoints())
            if (a.isInside(p))
                points.add(p);
        points.addAll(Shape2DUtil.getContactPoint(a, b));
        AbstractVector2f[] array = new AbstractVector2f[points.size()];
        return getCenter(points.toArray(array));
    }

    public static AbstractVector2f getCenter(AbstractVector2f[] points) {
        var sum = new Vector2f();
        for (var p : points)
            sum.plusAssign(p);
        return sum.div(points.length);
    }

    public static AbstractVector3f getCollisionPoint(IShape3D a, IShape3D b) {
        Collection<AbstractVector3f> points = new ArrayList<>();
        for (var p : a.getPoints())
            if (b.isInside(p))
                points.add(p);
        for (var p : b.getPoints())
            if (a.isInside(p))
                points.add(p);
        AbstractVector3f[] array = new AbstractVector3f[points.size()];
        return getCenter(points.toArray(array));
    }

    public static AbstractVector3f getCenter(AbstractVector3f[] points) {
        var sum = new Vector3f();
        for (var p : points)
            sum.plusAssign(p);
        return sum.div(points.length);
    }

    /** @return cos of ABC angle */
    public static float getCos(AbstractVector2f a, AbstractVector2f b, AbstractVector2f c) {
        AbstractVector2f v1 = b.minus(a);
        AbstractVector2f v2 = b.minus(c);
        return v1.dot(v2) / v1.length() / v2.length();
    }

    public static AbstractVector2f[] getMinimalConvexHullGraham(AbstractVector2f... points) {
        AbstractVector2f[] clone = new AbstractVector2f[points.length];
        System.arraycopy(points, 0, clone, 0, points.length);
        {
            int i = Mathf.minIndex(AbstractVector2f::x, clone);
            var temp = clone[0];
            clone[0] = clone[i];
            clone[i] = temp;
            var A = clone[0];
            Arrays.sort(clone, 1, clone.length, (a, b) -> (int) (getSin(a, b, A) / Mathf.EPS));
        }
        List<AbstractVector2f> temp = new ArrayList<>(clone.length);
        temp.add(clone[0]);
        temp.add(clone[1]);
        for (int i = 2; i < clone.length; i++) {
            var sin = getSin(temp.get(temp.size() - 2), temp.get(temp.size() - 1), clone[i]);
            if (sin > 0)
                temp.remove(temp.size() - 1);
            temp.add(clone[i]);
        }
        AbstractVector2f[] copy = new AbstractVector2f[temp.size()];
        temp.toArray(copy);
        return copy;
    }

    /** @return sin of ABC angle */
    public static float getSin(AbstractVector2f a, AbstractVector2f b, AbstractVector2f c) {
        AbstractVector2f v1 = b.minus(a);
        AbstractVector2f v2 = b.minus(c);
        float sin = v1.cross(v2);
        return sin / v1.length() / v2.length();
    }

    @Deprecated
    public static AbstractVector2f[] getMinimalConvexHullJarvis(AbstractVector2f... points) {
        var v = Mathf.minElement(AbstractVector2f::x, points);
        final var v0 = v;
        final List<AbstractVector2f> res = new ArrayList<>(points.length),
                temp = new ArrayList<>(points.length);
        Collections.addAll(temp, points);
        do {
            res.add(v);
            temp.remove(v);
            final var fv = v;
            v = Mathf.minElement(temp, a -> {
                var vec = a.minus(fv);
                float sin = vec.cross(vec2f(1, 0));
                return sin;
            });
        } while (v != v0);
        AbstractVector2f[] copy = new AbstractVector2f[res.size()];
        res.toArray(copy);
        return copy;
    }

    public static MathLine1D getProjection(AbstractVector2f[] points, AbstractVector2f normal) {
        return new MathLine1D(points, p -> VectorGeometryUtil.getProjectionPoint(p, normal));
    }

    public static float getProjectionPoint(AbstractVector2f point, AbstractVector2f normal) {
        var m = new MathLine2D(normal).minPoint(point);
        var c = point.cross(normal);
        c = c / Mathf.abs(c);
        return c * m.length();
    }

    public static MathLine1D getProjectionX(AbstractVector2f[] points) {
        return new MathLine1D(points, AbstractVector2f::x);
    }

    public static MathLine1D getProjectionY(AbstractVector2f[] points) {
        return new MathLine1D(points, AbstractVector2f::y);
    }

    public static FinalVector2f[] getUnitCirclePoints() {
        return UNIT_CIRCLE_POINTS;
    }

    public static AbstractVector2f[] getVectors2f(final float... point) {
        if ((point.length & 1) == 1)
            throw new UnsupportedOperationException(
                    "the length of the array must be even " + Arrays.toString(point));
        final AbstractVector2f[] res = new AbstractVector2f[point.length / 2];
        for (int i = 0; i < point.length; i += 2)
            res[i / 2] = new FinalVector2f(point[i], point[i + 1]);
        return res;
    }

    public static Vector3f[] getVectors3f(final float... point) {
        if (point.length % 3 == 0)
            throw new UnsupportedOperationException(
                    "the length of the array not correct " + Arrays.toString(point));
        final Vector3f[] res = new Vector3f[point.length / 3];
        for (int i = 0; i < point.length; i += 3)
            res[i / 3] = new Vector3f(point[i], point[i + 1], point[i + 2]);
        return res;
    }

    @SafeVarargs
    public static <T extends AbstractVector2f> boolean isClockwise(T... points) {
        var A = points[0];
        for (int i = 2; i < points.length; i++) {
            float ang = getSin(points[i - 1], points[i], A);
            if (ang < 0)
                return false;
            if (ang > 0)
                return true;
        }
        throw new UnsupportedOperationException("unreal");
    }

    public static <T extends AbstractVector2f> boolean isConvex(T[] pointsList) {
        AbstractVector2f[] points = cycle(pointsList, 2);
        float last = 0;
        for (int i = 2; i < points.length; i++) {
            float sin = getSin(points[i - 2], points[i - 1], points[i]);
            if (last * sin < 0)
                return false;
            last = sin;
        }
        return true;
    }

    public static <T extends AbstractVector2f> T[] cycle(T[] pointsList, int c) {
        T[] points = Arrays.copyOf(pointsList, pointsList.length + c);
        for (int i = 0; i < c; i++)
            points[points.length - c + i] = points[i];
        return points;
    }

    public static boolean isIntersectOnNormalProjection(FiniteShape2D a, FiniteShape2D b) {
        Set<AbstractVector2f> normals = new HashSet<>();
        normals.addAll(a.getNormals());
        normals.addAll(b.getNormals());
        for (var n : normals) {
            var pa = a.projection(n);
            var pb = b.projection(n);
            if (!pa.isIntersect(pb))
                return false;
        }
        return true;
    }

    public static boolean isIntersectOnPointInside(FiniteShape2D a, FiniteShape2D b) {
        for (var p : a.getPoints())
            if (b.isInside(p))
                return true;
        for (var p : b.getPoints())
            if (a.isInside(p))
                return true;
        return false;
    }

    public static Line2D[] toLineLoop(final AbstractVector2f[] points) {
        Line2D[] dest = new Line2D[points.length];
        dest[dest.length - 1] = new Line2D(points[points.length - 1], points[0]);
        toLineStrip(points, dest);
        return dest;
    }

    public static Line2D[] toLineStrip(final AbstractVector2f[] points, Line2D[] dest) {
        for (int i = 0; i < points.length - 1; i++)
            dest[i] = new Line2D(points[i], points[i + 1]);
        return dest;
    }

    public static Line2D[] toLineStrip(final AbstractVector2f[] points) {
        Line2D[] dest = new Line2D[points.length - 1];
        toLineStrip(points, dest);
        return dest;
    }

    public static List<Triangle2D> triangulation(AbstractVector2f... points) {
        return triangulation0(points.clone());
    }

    public static Collection<Triangle2D> triangulation(FiniteShape2D shape) {
        return triangulation(shape.getPoints());
    }

    public static Collection<Triangle2D> triangulation(Iterable<AbstractVector2f> iter) {
        return triangulation(
                IteratorUtil.array(iter, new AbstractVector2f[IteratorUtil.size(iter)]));
    }

    public static int lastIndexConvex(AbstractVector2f[] points) {
        final var vlines = new AbstractVector2f[points.length + 1];
        for (int i = 0; i < points.length - 1; i++) {
            final var p0 = points[i];
            final var p1 = points[i + 1];
            vlines[i] = p1.minus(p0);
        }
        {
            final var p0 = points[points.length - 1];
            final var p1 = points[0];
            vlines[vlines.length - 2] = p1.minus(p0);
        }
        {
            vlines[vlines.length - 1] = vlines[0];
        }
        for (int i = vlines.length - 2; i >= 0; i--) {
            final var l0 = vlines[i];
            final var l1 = vlines[i + 1];
            if (l0.cross(l1) > 0)
                return (i + 1) % points.length;
        }
        throw new RuntimeException("unreal Exception");
    }

    private static List<Triangle2D> triangulation0(AbstractVector2f[] points) {
        final var len = points.length;
        if (len > 3) {
            final var ic = lastIndexConvex(points);
            final var as = new AbstractVector2f[3];
            for (int i = 0; i < as.length; i++)
                as[i] = points[((i + ic - 1) % points.length + points.length) % points.length];
            final var a = triangulation(as);
            final var bs = remove(points, ic);
            final var b = triangulation(bs);
            return union(a, b);
        }
        if (len == 3) {
            final var p0 = new Vector2f(1, 0);
            Arrays.sort(points, 1, points.length, Comparator
                    .comparing(p -> p.minus(points[0]).normalize(1).cross(p0)));
            return List.of(new Triangle2D(points[0], points[1], points[2]));
        }
        throw new UnsupportedOperationException("points.length=" + points.length);
    }

    private static AbstractVector2f[] remove(AbstractVector2f[] points, int ic) {
        final var res = new AbstractVector2f[points.length - 1];
        System.arraycopy(points, 0, res, 0, ic);
        System.arraycopy(points, ic + 1, res, ic, points.length - ic - 1);
        return res;
    }

    private static List<Triangle2D> union(List<Triangle2D> a, List<Triangle2D> b) {
        final var res = new ArrayList<>(a);
        res.addAll(b);
        return res;
    }

}

