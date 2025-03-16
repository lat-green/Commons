package com.greentree.commons.geometry.geom3d

import com.greentree.commons.geometry.geom2d.isIntersect
import com.greentree.commons.geometry.geom2d.shape.Polygon2D
import com.greentree.commons.geometry.geom3d.operation.Shape3DBinaryOperations
import com.greentree.commons.math.vec4f
import com.greentree.commons.util.iterator.size
import org.joml.Matrix3f
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector3fc
import org.joml.minus
import org.joml.plusAssign

object Shape3DUtil : Shape3DBinaryOperations() {

	@JvmStatic
	fun getPoints(points: Array<out Vector3fc>, model: Matrix4f): Array<Vector3fc> = Array(points.size) {
		vec4f(points[it], 1f).mul(model).xyz(
			Vector3f()
		)
	}

	@JvmStatic
	fun getProjectionPoligon(shape: IShape3D, normal: Vector3fc): Polygon2D {
		return Polygon2D(shape.getProjection(normal))
	}

	@JvmStatic
	fun getRadius(center: Vector3fc, points: Array<Vector3fc>): Float {
		return points.maxOf { it.distanceSquared(center) }
	}

	/** @return sin of ABC angle
	 */
	@JvmStatic
	fun getSin(a: Vector3fc, b: Vector3fc, c: Vector3fc): Float {
		val v1 = a.minus(b)
		val v2 = c.minus(b)
		val cr = v1.cross(v2)
		val len = cr.length() / v1.length() / v2.length()
		if(isRight(a, b, c)) return -len
		return len
	}

	fun isRight(a: Vector3fc, b: Vector3fc, c: Vector3fc): Boolean {
		return Matrix3f(a.x(), a.y(), a.z(), b.x(), b.y(), b.z(), c.x(), c.y(), c.z())
			.determinant() > 0
	}

	@JvmStatic
	fun <A : IShape3D?, B : IShape3D?> isIntersectOnNormalProjection(
		a: A, b: B
	): Boolean {
		for(an in a!!.normals) {
			val ap = Polygon2D(a.getProjection(an))
			val bp = Polygon2D(a.getProjection(an))
			if(!ap.isIntersect(bp)) return false
		}
		return true
	}

	@JvmStatic
	fun getCenter(points: Array<out Vector3fc>): Vector3fc {
		val sum = Vector3f()
		for(p in points) sum.plusAssign(p)
		return sum.div(points.size.toFloat())
	}

	@JvmStatic
	fun getCenter(points: Iterable<Vector3fc>): Vector3fc {
		val sum = Vector3f()
		for(p in points) sum.plusAssign(p)
		return sum.div(points.size.toFloat())
	}
//	public static Vector3fc getCollisionNormalOnNormalProjection(IShape3D a, IShape3D b) {
	//		final Vector3fc n1 = getCollisionNormalOnNormalProjection0(a, b);
	//		final Vector3fc n2 = getCollisionNormalOnNormalProjection0(b, a).mul(-1);
	//		final float o1 = getProjectionOverlay(a, b, n1);
	//		final float o2 = getProjectionOverlay(a, b, n2);
	//		if(o1 < o2)
	//			return n1;
	//		else
	//			return n2;
	//	}
	//
	//	public static float getProjectionOverlay(IShape3D a, IShape3D b, Vector3fc normal) {
	//		var s1 = getProjectionPoligon(a, normal);
	//		var s2 = getProjectionPoligon(b, normal);
	//		return Shape2DUtil.getProjectionOverlay(s1, s2);
	//	}
	//	public static Vector3fc getCollisionNormalOnNormalProjection0(IShape3D a, IShape3D b) {
	//		Vector3fc[] normals;
	//		var rv = b.getCenter().sub(a.getCenter(), new Vector3f()).normalize();
	//		{
	//			Collection<Vector3fc> normals0 = new ArrayList<>();
	//			Collections.addAll(normals0, a.getNormals());
	//			if(rv.lengthSquared() > 0)
	//				normals0 = normals0.parallelStream().filter(n -> n.dot(rv) > 0).collect(Collectors.toList());
	//			normals = new Vector3f[normals0.size()];
	//			normals0.toArray(normals);
	//		}
	//		Vector3fc res_normal = normals[0];
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
