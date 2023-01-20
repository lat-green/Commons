package com.greentree.commons.geometry.geom2d.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.joml.Math;

import com.greentree.commons.geometry.geom2d.ILine2D;
import com.greentree.commons.geometry.geom2d.IShape2D;
import com.greentree.commons.geometry.geom2d.Line2DImpl;
import com.greentree.commons.geometry.geom2d.Shape2DUtil;
import com.greentree.commons.geometry.geom2d.shape.Triangle2D;
import com.greentree.commons.geometry.geom3d.IShape3D;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.geometry.MathLine1D;
import com.greentree.commons.math.geometry.MathLine2D;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.commons.math.vector.FinalVector2f;
import com.greentree.commons.math.vector.Vector2f;
import com.greentree.commons.math.vector.Vector3f;
import com.greentree.commons.util.iterator.IteratorUtil;

public abstract class VectorGeometryUtil {
	
	
	
	public static final int POINT_IN_CIRCLE = 30;
	
	private static final FinalVector2f[] UNIT_CIRCLE_POINTS = new FinalVector2f[POINT_IN_CIRCLE];
	
	static {
		final float delta = 2 * Mathf.PI / POINT_IN_CIRCLE;
		for(int i = 0; i < POINT_IN_CIRCLE; i++) {
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
	
	public static <T extends AbstractVector2f> T[] cycle(T[] pointsList, int c) {
		T[] points = Arrays.copyOf(pointsList, pointsList.length + c);
		
		for(int i = 0; i < c; i++)
			points[points.length - c + i] = points[i];
		
		return points;
	}
	
	public static <T extends AbstractVector2f> boolean equals(T[] a, T[] b) {
		if(a.length != b.length)
			return false;
		A :
		for(int s = 0; s < a.length; s++) {
			for(int i = 0; i < a.length; i++)
				if(!a[(i + s) % a.length].equals(b[i]))
					continue A;
			return true;
		}
		return false;
	}
	
	/** @return angle of ABC angle */
	public static float getAngle(AbstractVector2f a, AbstractVector2f b, AbstractVector2f c) {
		AbstractVector2f v1 = b.sub(a, new Vector2f()).normalize();
		AbstractVector2f v2 = b.sub(c, new Vector2f()).normalize();
		float sin = v1.cross(v2);
		float cos = v1.dot(v2);
		return Math.atan2(sin, cos);
	}
	
	public static Vector2f getCenter(AbstractVector2f[] points) {
		Vector2f sum = new Vector2f();
		for(var p : points)
			sum.add(p);
		sum.mul(1f / points.length);
		return sum;
	}
	
	public static Vector3f getCenter(AbstractVector3f[] points) {
		var sum = new Vector3f();
		for(var p : points)
			sum.add(p);
		sum.mul(1f / points.length);
		return sum;
	}
	
	public static Vector2f[] getCirclePoints(final float centerx, final float centery,
			final float radius) {
		Vector2f[] vectors = new Vector2f[POINT_IN_CIRCLE];
		for(int i = 0; i < vectors.length; i++) {
			var v = UNIT_CIRCLE_POINTS[i];
			vectors[i] = new Vector2f(centerx + radius * v.x, centery + radius * v.y);
		}
		return vectors;
	}
	
	
	public static Vector2f getCollisionNormalOnNormalProjection(IShape2D a, IShape2D b) {
		final Vector2f n1 = getCollisionNormalOnNormalProjection0(a, b);
		final Vector2f n2 = getCollisionNormalOnNormalProjection0(b, a).mul(-1);
		final float o1 = getProjectionOverlay(a, b, n1);
		final float o2 = getProjectionOverlay(a, b, n2);
		if(o1 < o2)
			return n1;
		else
			return n2;
	}
	
	public static Vector2f getCollisionNormalOnNormalProjection0(IShape2D a, IShape2D b) {
		Vector2f[] normals;
		var rv = b.getCenter().sub(a.getCenter(), new Vector2f()).normalize();
		{
			Collection<Vector2f> normals0;
			if(rv.lengthSquared() > 0)
				normals0 = a.getNormals().parallelStream().filter(n->n.dot(rv) > 0)
						.collect(Collectors.toList());
			else
				normals0 = a.getNormals();
			normals = new Vector2f[normals0.size()];
			normals0.toArray(normals);
		}
		Vector2f res_normal = normals[0];
		{
			float res_overlay = getProjectionOverlay(a, b, res_normal);
			for(int i = 1; i < normals.length; i++) {
				final Vector2f normal = normals[i];
				final float overlay = getProjectionOverlay(a, b, normal);
				if(overlay <= 0)
					continue;
				if(overlay < res_overlay) {
					res_normal = normal;
					res_overlay = overlay;
				}
			}
		}
		res_normal = new Vector2f(res_normal.y, -res_normal.x);
		if(res_normal.dot(rv) < 0)
			res_normal.mul(-1);
		return res_normal;
	}
	
	public static AbstractVector2f getCollisionPoint(IShape2D a, IShape2D b) {
		Collection<AbstractVector2f> points = new ArrayList<>();
		for(AbstractVector2f p : a.getPoints())
			if(b.isInside(p))
				points.add(p);
		for(AbstractVector2f p : b.getPoints())
			if(a.isInside(p))
				points.add(p);
		points.addAll(Shape2DUtil.getContactPoint(a, b));
		AbstractVector2f[] array = new AbstractVector2f[points.size()];
		return getCenter(points.toArray(array));
	}
	
	public static AbstractVector3f getCollisionPoint(IShape3D a, IShape3D b) {
		Collection<AbstractVector3f> points = new ArrayList<>();
		for(var p : a.getPoints())
			if(b.isInside(p))
				points.add(p);
		for(var p : b.getPoints())
			if(a.isInside(p))
				points.add(p);
		AbstractVector3f[] array = new AbstractVector3f[points.size()];
		return getCenter(points.toArray(array));
	}
	
	/** @return cos of ABC angle */
	public static float getCos(AbstractVector2f a, AbstractVector2f b, AbstractVector2f c) {
		AbstractVector2f v1 = b.sub(a, new Vector2f());
		AbstractVector2f v2 = b.sub(c, new Vector2f());
		return v1.dot(v2) / v1.length() / v2.length();
	}
	
	public static AbstractVector2f[] getMinimalConvexHullGraham(AbstractVector2f... points) {
		AbstractVector2f[] clone = new AbstractVector2f[points.length];
		for(int i = 0; i < points.length; i++)
			clone[i] = points[i];
		{
			int i = Mathf.minIndex(AbstractVector2f::x, clone);
			var temp = clone[0];
			clone[0] = clone[i];
			clone[i] = temp;
			var A = clone[0];
			Arrays.sort(clone, 1, clone.length, (a, b)->(int) (getSin(a, b, A) / Mathf.EPS));
		}
		List<AbstractVector2f> temp = new ArrayList<>(clone.length);
		
		temp.add(clone[0]);
		temp.add(clone[1]);
		for(int i = 2; i < clone.length; i++) {
			var sin = getSin(temp.get(temp.size() - 2), temp.get(temp.size() - 1), clone[i]);
			if(sin > 0)
				temp.remove(temp.size() - 1);
			temp.add(clone[i]);
		}
		
		AbstractVector2f[] copy = new AbstractVector2f[temp.size()];
		temp.toArray(copy);
		return copy;
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
			v = Mathf.minElement(temp, a-> {
				var vec = a.sub(fv, new Vector2f());
				float sin = vec.cross(new Vector2f(1, 0));
				return sin;
			});
		}while(v != v0);
		
		AbstractVector2f[] copy = new AbstractVector2f[res.size()];
		res.toArray(copy);
		return copy;
	}
	
	public static MathLine1D getProjection(AbstractVector2f[] points, AbstractVector2f normal) {
		return new MathLine1D(points, p->VectorGeometryUtil.getProjectionPoint(p, normal));
	}
	
	public static float getProjectionOverlay(IShape2D a, IShape2D b, AbstractVector2f normal) {
		return a.getProjection(normal).getOverlay(b.getProjection(normal));
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
	
	/** @return sin of ABC angle */
	public static float getSin(AbstractVector2f a, AbstractVector2f b, AbstractVector2f c) {
		AbstractVector2f v1 = b.sub(a, new Vector2f());
		AbstractVector2f v2 = b.sub(c, new Vector2f());
		float sin = v1.cross(v2);
		return sin / v1.length() / v2.length();
	}
	
	public static FinalVector2f[] getUnitCirclePoints() {
		return UNIT_CIRCLE_POINTS;
	}
	
	public static Vector2f[] getVectors2f(final float... point) {
		if((point.length & 1) == 1)
			throw new UnsupportedOperationException(
					"the length of the array must be even " + Arrays.toString(point));
		final Vector2f[] res = new Vector2f[point.length / 2];
		for(int i = 0; i < point.length; i += 2)
			res[i / 2] = new Vector2f(point[i], point[i + 1]);
		return res;
	}
	
	public static Vector3f[] getVectors3f(final float... point) {
		if(point.length % 3 == 0)
			throw new UnsupportedOperationException(
					"the length of the array not correct " + Arrays.toString(point));
		final Vector3f[] res = new Vector3f[point.length / 3];
		for(int i = 0; i < point.length; i += 3)
			res[i / 3] = new Vector3f(point[i], point[i + 1], point[i + 2]);
		return res;
	}
	
	@SafeVarargs
	public static <T extends AbstractVector2f> boolean isClockwise(T... points) {
		var A = points[0];
		for(int i = 2; i < points.length; i++) {
			float ang = getSin(points[i - 1], points[i], A);
			if(ang < 0)
				return false;
			if(ang > 0)
				return true;
		}
		throw new UnsupportedOperationException("unreal");
	}
	
	public static <T extends AbstractVector2f> boolean isConvex(T[] pointsList) {
		AbstractVector2f[] points = cycle(pointsList, 2);
		float last = 0;
		for(int i = 2; i < points.length; i++) {
			float sin = getSin(points[i - 2], points[i - 1], points[i]);
			if(last * sin < 0)
				return false;
			last = sin;
		}
		return true;
	}
	
	
	public static boolean isIntersectOnNormalProjection(IShape2D a, IShape2D b) {
		Set<AbstractVector2f> normals = new HashSet<>();
		
		normals.addAll(a.getNormals());
		normals.addAll(b.getNormals());
		
		for(var n : normals) {
			var pa = a.getProjection(n);
			var pb = b.getProjection(n);
			
			if(!pa.isIntersect(pb))
				return false;
		}
		
		return true;
	}
	
	public static boolean isIntersectOnPointInside(IShape2D a, IShape2D b) {
		for(var p : a.getPoints())
			if(b.isInside(p))
				return true;
		for(var p : b.getPoints())
			if(a.isInside(p))
				return true;
		return false;
	}
	
	public static ILine2D[] toLineLoop(final AbstractVector2f[] points) {
		ILine2D[] dest = new ILine2D[points.length];
		dest[dest.length - 1] = new Line2DImpl(points[points.length - 1], points[0]);
		toLineStrip(points, dest);
		return dest;
	}
	
	public static ILine2D[] toLineStrip(final AbstractVector2f[] points) {
		ILine2D[] dest = new ILine2D[points.length - 1];
		toLineStrip(points, dest);
		return dest;
	}
	
	public static ILine2D[] toLineStrip(final AbstractVector2f[] points, ILine2D[] dest) {
		for(int i = 0; i < points.length - 1; i++)
			dest[i] = new Line2DImpl(points[i], points[i + 1]);
		return dest;
	}
	
	public static List<Triangle2D> triangulation(AbstractVector2f... points) {
		return triangulation0(points.clone());
	}
	
	public static Collection<Triangle2D> triangulation(IShape2D shape) {
		return triangulation(shape.getPoints());
	}
	
	public static Collection<Triangle2D> triangulation(Iterable<AbstractVector2f> iter) {
		return triangulation(
				IteratorUtil.array(iter, new AbstractVector2f[IteratorUtil.size(iter)]));
	}
	
	public static int lastIndexConvex(AbstractVector2f[] points) {
		final var vlines = new Vector2f[points.length + 1];
		for(int i = 0; i < points.length - 1; i++) {
			final var p0 = points[i + 0];
			final var p1 = points[i + 1];
			vlines[i] = p1.sub(p0, new Vector2f());
		}
		{
			final var p0 = points[points.length - 1];
			final var p1 = points[0];
			vlines[vlines.length - 2] = p1.sub(p0, new Vector2f());
		}
		{
			vlines[vlines.length - 1] = vlines[0];
		}
		for(int i = vlines.length - 2; i >= 0; i--) {
			final var l0 = vlines[i + 0];
			final var l1 = vlines[i + 1];
			if(l0.cross(l1) > 0)
				return (i + 1) % points.length;
		}
		throw new RuntimeException("unreal Exception");
	}
	
	private static List<Triangle2D> triangulation0(AbstractVector2f[] points) {
		final var len = points.length;
		if(len > 3) {
			final var ic = lastIndexConvex(points);
			
			final var as = new AbstractVector2f[3];
			for(int i = 0; i < as.length; i++)
				as[i] = points[((i + ic - 1) % points.length + points.length) % points.length];
			
			final var a = triangulation(as);
			final var bs = remove(points, ic);
			final var b = triangulation(bs);
			return union(a, b);
		}
		if(len == 3) {
			final var p0 = new Vector2f(1, 0);
			Arrays.sort(points, 1, points.length, Comparator
					.comparing(p->p.sub(points[0], new Vector2f()).normalize().cross(p0)));
			return Arrays.asList(new Triangle2D(points[0], points[1], points[2]));
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

