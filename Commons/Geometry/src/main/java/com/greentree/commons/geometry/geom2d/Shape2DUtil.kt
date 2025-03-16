package com.greentree.commons.geometry.geom2d

import com.greentree.commons.geometry.geom2d.collision.Collidable2D
import com.greentree.commons.geometry.geom2d.collision.CollisionEvent2D
import com.greentree.commons.geometry.geom2d.operation.CircleVsCircle
import com.greentree.commons.geometry.geom2d.operation.CircleVsRectangle
import com.greentree.commons.geometry.geom2d.operation.RectangleVsRectangle
import com.greentree.commons.geometry.geom2d.operation.Shape2DBinaryOperation
import com.greentree.commons.geometry.geom2d.operation.SwapBinaryOperation2D
import com.greentree.commons.geometry.geom2d.shape.Circle2D
import com.greentree.commons.geometry.geom2d.shape.FiniteShape2D
import com.greentree.commons.geometry.geom2d.shape.Line2D
import com.greentree.commons.geometry.geom2d.shape.Polygon2D
import com.greentree.commons.geometry.geom2d.shape.Rectangle2D
import com.greentree.commons.math.MathLine1D
import com.greentree.commons.math.MathLine2D
import com.greentree.commons.math.Mathf
import com.greentree.commons.math.Mathf.acos
import com.greentree.commons.math.Mathf.sqrt
import com.greentree.commons.math.cross
import com.greentree.commons.math.vec2f
import com.greentree.commons.util.collection.Table
import com.greentree.commons.util.iterator.size
import org.joml.Vector2f
import org.joml.Vector2fc
import org.joml.Vector3f
import org.joml.Vector3fc
import org.joml.minus
import org.joml.plusAssign
import org.joml.unaryMinus
import java.util.*
import java.util.function.Predicate
import kotlin.math.max
import kotlin.math.min

object Shape2DUtil {

	private val table = Table<Class<out Shape2D>, Class<out Shape2D>, Shape2DBinaryOperation<*, *>>()

	init {
		add(Circle2D::class.java, Circle2D::class.java, CircleVsCircle())
		add(
			Circle2D::class.java,
			Rectangle2D::class.java, CircleVsRectangle()
		)
		add(
			Rectangle2D::class.java,
			Rectangle2D::class.java, RectangleVsRectangle()
		)
	}

	fun getMinkowskiAdd(a: FiniteShape2D, b: FiniteShape2D): Shape2D {
		val list: MutableList<Vector2fc> = ArrayList()
		for(p1 in a.points) for(p2 in b.points) {
			val point = Vector2f(p1.x() + p2.x(), p1.y() + p2.y())
			if(!list.contains(point)) list.add(point)
		}
		return Polygon2D(list)
	}

	fun <A : Shape2D, B : Shape2D> add(
		classA: Class<A>,
		classB: Class<B>, collisionHendler2D: Shape2DBinaryOperation<A, B>
	) {
		if(classA != classB) table.put(classB, classA, SwapBinaryOperation2D(collisionHendler2D))
		table.put(classA, classB, collisionHendler2D)
	}

	fun contactLine(line: Line2D, line_no_border: Line2D?): Vector2fc? {
		val p = contact(line, line_no_border)
		val max_x = max(line.p1().x().toDouble(), line.p2().x().toDouble()).toFloat()
		val min_x = line.p1().x() + line.p2().x() - max_x
		val max_y = max(line.p1().y().toDouble(), line.p2().y().toDouble()).toFloat()
		val min_y = line.p1().y() + line.p2().y() - max_y
		if(p!!.x() > max_x || p.x() < min_x || p.y() > max_y || p.y() < min_y) return null
		return p
	}

	fun contact(a: Line2D, b: Line2D?): Vector2f? {
		return MathLine2D.contact(a.mathLine, a.mathLine)
	}

	fun contactWithBorder(a: Line2D, b: Line2D): Vector2fc? {
		val res = contact(a, b)
		if(res == null || !a.boundingBox.isInside(res) || !b.boundingBox.isInside(res)) return null
		return res
	}

	fun distanse(p: Vector2fc, a: Collection<Shape2D>): Float {
		var dis = Float.MAX_VALUE
		for(l in a) dis = min(dis.toDouble(), l.distance(p).toDouble()).toFloat()
		return dis
	}

	fun findAngles(
		pointsList: Array<Vector2f>,
		predicate: Predicate<Triple<Vector2f, Vector2f, Vector2f>>
	): List<Int> {
		val points = cycle(pointsList, 2)
		val res: MutableList<Int> = ArrayList()
		for(i in 0 ..< points.size - 2) {
			val t = Triple(points[i], points[i + 1], points[i + 2])
			if(predicate.test(t)) res.add(i)
		}
		return res
	}

	@JvmStatic
	fun <T : Collidable2D> getCollisions(
		contacts: Collection<Pair<T, T>>
	): Collection<CollisionEvent2D<T, T>> {
		val res: MutableCollection<CollisionEvent2D<T, T>> = ArrayList(contacts.size)
		contacts.parallelStream().forEach { pair: Pair<T, T> ->
			val event = getCollisionEvent(pair.first, pair.second)
			synchronized(event) {
				res.add(event)
			}
		}
		return res
	}

	@JvmStatic
	fun <A : Collidable2D, B : Collidable2D> getCollisionEvent(
		a: A, b: B
	): CollisionEvent2D<A, B> {
		val sa = a.shape
		val sb = b.shape
		return CollisionEvent2D(a, b, getHandler(sa, sb).getCollisionEvent(sa, sb))
	}

	private fun <A : Shape2D, B : Shape2D> getHandler(a: A, b: B) = getHandler(a.javaClass, b.javaClass)

	private fun <A : Shape2D, B : Shape2D> getHandler(
		a: Class<A>,
		b: Class<B>,
	): Shape2DBinaryOperation<A, B> {
		val res = table[a, b]
		if(res != null)
			return res as Shape2DBinaryOperation<A, B>
		return Shape2DBinaryOperation.DEFAULT as Shape2DBinaryOperation<A, B>
	}

	fun <A : Shape2D, B : Shape2D> getContactPoint(
		a: A, b: B
	): Collection<Vector2fc> {
		return getHandler(a, b).getContactPoint(a, b)
	}

	@JvmStatic
	fun <A : Shape2D, B : Shape2D> isIntersect(
		a: A,
		b: B,
	): Boolean {
		return getHandler(a, b).isIntersect(a, b)
	}

	@Deprecated("")
	fun toClockwisePoligon(vararg collection: Vector2fc): Polygon2D {
		val c = getCenter(collection)
		val list = ArrayList<Vector2fc>(collection.size)
		Collections.addAll(list, *collection)
		Collections.sort(list, Comparator.comparingDouble { p: Vector2fc ->
			var cos: Float = p.x() - c.x()
			var sin: Float = p.y() - c.y()
			var len = cos * cos + sin * sin
			if(len < 1E-9) return@comparingDouble 0.0
			len = sqrt(len)
			cos /= len
			sin /= len
			var ang = acos(cos)
			if(sin < 0) ang *= -1f
			ang.toDouble()
		})
		return Polygon2D(list)
	}

	const val POINT_IN_CIRCLE: Int = 30
	val unitCirclePoints: Array<Vector2f?> = arrayOfNulls(POINT_IN_CIRCLE)

	init {
		val delta = 2 * Mathf.PI / POINT_IN_CIRCLE
		for(i in 0 ..< POINT_IN_CIRCLE) {
			val d = Mathf.PI2 + delta * i
			val sin = Mathf.sin(d)
			val cos = Mathf.cos(d, sin)
			unitCirclePoints[i] = Vector2f(cos, sin)
		}
	}

	fun areaTriangle(a: Vector2fc, b: Vector2fc, c: Vector2fc): Float {
		return (b.x() - a.x()) * (c.y() - a.y()) - (b.y() - a.y()) * (c.x() - a.x())
	}

	fun <T : Vector2fc?> equals(a: Array<T>, b: Array<T>): Boolean {
		if(a.size != b.size) return false
		A@ for(s in a.indices) {
			for(i in a.indices) if(a[(i + s) % a.size] != b[i]) continue@A
			return true
		}
		return false
	}

	/** @return angle of ABC angle
	 */
	fun getAngle(a: Vector2fc, b: Vector2fc, c: Vector2fc): Float {
		val v1 = (b - a).normalize(1f)
		val v2 = (b - c).normalize(1f)
		val sin = v1.cross(v2)
		val cos = v1.dot(v2)
		return Mathf.atan2(sin, cos)
	}

	fun getCirclePoints(
		centerx: Float, centery: Float,
		radius: Float
	): Array<Vector2fc?> {
		val vectors = arrayOfNulls<Vector2fc>(POINT_IN_CIRCLE)
		for(i in vectors.indices) {
			val v = unitCirclePoints[i]
			vectors[i] = Vector2f(centerx + radius * v!!.x(), centery + radius * v.y())
		}
		return vectors
	}

	fun getCollisionNormalOnNormalProjection(a: FiniteShape2D, b: FiniteShape2D): Vector2fc {
		val n1 = getCollisionNormalOnNormalProjection0(a, b)
		val n2 = -getCollisionNormalOnNormalProjection0(b, a)
		val o1 = getProjectionOverlay(a, b, n1)
		val o2 = getProjectionOverlay(a, b, n2)
		return if(o1 < o2) n1
		else n2
	}

	fun getCollisionNormalOnNormalProjection0(a: FiniteShape2D, b: FiniteShape2D): Vector2fc {
		val normals: Array<Vector2fc>
		val rv = (b.boundingCircle.center - a.boundingCircle.center).normalize(1f)
		run {
			val normals0 =
				if(rv.lengthSquared() > 0) a.normals.parallelStream().filter { n: Vector2fc -> n.dot(rv) > 0 }
					.collect(java.util.stream.Collectors.toList<Vector2fc>())
				else a.normals
			normals = normals0.toTypedArray<Vector2fc>()
		}
		var res_normal = normals[0]
		run {
			var res_overlay = getProjectionOverlay(a, b, res_normal)
			for(i in 1 ..< normals.size) {
				val normal = normals[i]
				val overlay = getProjectionOverlay(a, b, normal)
				if(overlay <= 0) continue
				if(overlay < res_overlay) {
					res_normal = normal
					res_overlay = overlay
				}
			}
		}
		res_normal = Vector2f(res_normal.y(), -res_normal.x())
		if(res_normal.dot(rv) < 0)
			return -res_normal
		return res_normal
	}

	fun getProjectionOverlay(a: Shape2D, b: Shape2D, normal: Vector2fc): Float {
		return a.projection(normal).getOverlay(b.projection(normal))
	}

	fun getCollisionPoint(a: FiniteShape2D, b: FiniteShape2D): Vector2fc {
		val points: MutableCollection<Vector2fc> = ArrayList()
		for(p in a.points) if(b.isInside(p)) points.add(p)
		for(p in b.points) if(a.isInside(p)) points.add(p)
		points.addAll(getContactPoint(a, b))
		return getCenter(points)
	}

	fun getCenter(points: Array<out Vector2fc>): Vector2fc {
		val sum = Vector2f()
		for(p in points) sum.plusAssign(p)
		return sum.div(points.size.toFloat())
	}

	fun getCenter(points: Iterable<Vector2fc>): Vector2fc {
		val sum = Vector2f()
		for(p in points) sum.plusAssign(p)
		return sum.div(points.size.toFloat())
	}

	fun getCollisionPoint(
		a: com.greentree.commons.geometry.geom3d.IShape3D,
		b: com.greentree.commons.geometry.geom3d.IShape3D
	): Vector3fc {
		val points = ArrayList<Vector3fc>()
		for(p in a.points)
			if(b.isInside(p)) points.add(p)
		for(p in b.points)
			if(a.isInside(p)) points.add(p)
		return getCenter(points.toTypedArray())
	}

	fun getCenter(points: Array<Vector3fc>): Vector3fc {
		val sum = Vector3f()
		for(p in points) sum.plusAssign(p)
		return sum.div(points.size.toFloat())
	}

	/** @return cos of ABC angle
	 */
	fun getCos(a: Vector2fc, b: Vector2fc, c: Vector2fc): Float {
		val v1 = b - a
		val v2 = b - c
		return v1.dot(v2) / v1.length() / v2.length()
	}

	@JvmStatic
	fun getMinimalConvexHullGraham(vararg points: Vector2fc): Array<Vector2fc> {
		val clone = points.copyOf() as Array<Vector2fc>
		run {
			val i = Mathf.minIndex({ it.x() }, *clone)
			val temp = clone[0]
			clone[0] = clone[i]
			clone[i] = temp
			val A = clone[0]
			Arrays.sort(clone, 1, clone.size) { a, b -> (getSin(a, b, A) / Mathf.EPS).toInt() }
		}
		val temp = ArrayList<Vector2fc>(clone.size)
		temp.add(clone[0])
		temp.add(clone[1])
		for(i in 2 ..< clone.size) {
			val sin = getSin(
				temp[temp.size - 2],
				temp[temp.size - 1], clone[i]
			)
			if(sin > 0) temp.removeAt(temp.size - 1)
			temp.add(clone[i])
		}
		return temp.toTypedArray()
	}

	/** @return sin of ABC angle
	 */
	fun getSin(a: Vector2fc, b: Vector2fc, c: Vector2fc): Float {
		val v1 = b - a
		val v2 = b - c
		val sin = v1.cross(v2)
		return sin / v1.length() / v2.length()
	}

	@Deprecated("")
	fun getMinimalConvexHullJarvis(vararg points: Vector2fc): Array<Vector2fc> {
		var v = points.minBy { it.x() }
		val v0 = v
		val res = ArrayList<Vector2fc>(points.size)
		val temp = ArrayList<Vector2fc>(points.size)
		Collections.addAll(temp, *points)
		do {
			res.add(v)
			temp.remove(v)
			val fv = v
			v = temp.minBy {
				val vec = it.minus(fv)
				vec.cross(vec2f(1f, 0f))
			}
		} while(v !== v0)
		return res.toTypedArray()
	}

	fun getProjection(points: Array<Vector2fc>, normal: Vector2fc): MathLine1D {
		return MathLine1D(
			points
		) { getProjectionPoint(it, normal) }
	}

	fun getProjectionPoint(point: Vector2fc, normal: Vector2fc): Float {
		val m = MathLine2D(normal).minPoint(point)
		var c = point.cross(normal)
		c = c / Mathf.abs(c)
		return c * m.length()
	}

	fun getProjectionX(points: Array<Vector2fc>): MathLine1D {
		return MathLine1D(points) { it.x() }
	}

	fun getProjectionY(points: Array<Vector2fc>): MathLine1D {
		return MathLine1D(points) { it.y() }
	}

	@JvmStatic
	fun getVectors2f(vararg point: Float): Array<Vector2fc> {
		if((point.size and 1) == 1) throw UnsupportedOperationException(
			"the length of the array must be even " + point.contentToString()
		)
		return Array(point.size / 2) {
			Vector2f(
				point[2 * it + 0],
				point[2 * it + 1]
			)
		}
	}

	fun getVectors3f(vararg point: Float): Array<Vector3f?> {
		if(point.size % 3 == 0) throw UnsupportedOperationException(
			"the length of the array not correct " + point.contentToString()
		)
		val res = arrayOfNulls<Vector3f>(point.size / 3)
		var i = 0
		while(i < point.size) {
			res[i / 3] = Vector3f(point[i], point[i + 1], point[i + 2])
			i += 3
		}
		return res
	}

	@JvmStatic
	@SafeVarargs
	fun isClockwise(vararg points: Vector2fc): Boolean {
		val A = points[0]
		for(i in 2 ..< points.size) {
			val ang = getSin(points[i - 1], points[i], A)
			if(ang < 0) return false
			if(ang > 0) return true
		}
		throw UnsupportedOperationException("unreal")
	}

	@JvmStatic
	fun <T : Vector2fc> isConvex(pointsList: Array<T>): Boolean {
		val points = cycle(pointsList, 2)
		var last = 0f
		for(i in 2 ..< points.size) {
			val sin = getSin(points[i - 2], points[i - 1], points[i])
			if(last * sin < 0) return false
			last = sin
		}
		return true
	}

	fun <T : Vector2fc> cycle(pointsList: Array<T>, c: Int): Array<T> {
		val points = pointsList.copyOf(pointsList.size + c)
		for(i in 0 ..< c)
			points[points.size - c + i] = points[i]
		return points as Array<T>
	}

	fun isIntersectOnNormalProjection(a: FiniteShape2D, b: FiniteShape2D): Boolean {
		val normals: MutableSet<Vector2fc> = HashSet()
		normals.addAll(a.normals)
		normals.addAll(b.normals)
		for(n in normals) {
			val pa = a.projection(n)
			val pb = b.projection(n)
			if(!pa.isIntersect(pb)) return false
		}
		return true
	}

	fun isIntersectOnPointInside(a: FiniteShape2D, b: FiniteShape2D): Boolean {
		for(p in a.points) if(b.isInside(p)) return true
		for(p in b.points) if(a.isInside(p)) return true
		return false
	}

	fun toLineLoop(points: Array<out Vector2fc>): Array<Line2D> {
		val dest = arrayOfNulls<Line2D>(points.size) as Array<Line2D>
		dest[dest.size - 1] = Line2D(
			points[points.size - 1],
			points[0]
		)
		toLineStrip(points, dest)
		return dest
	}

	fun toLineStrip(points: Array<out Vector2fc>, dest: Array<Line2D>): Array<Line2D> {
		for(i in 0 ..< points.size - 1) dest[i] = Line2D(
			points[i],
			points[i + 1]
		)
		return dest
	}

	@JvmStatic
	fun toLineStrip(points: Array<out Vector2fc>): Array<Line2D> {
		return Array(points.size) {
			Line2D(
				points[2 * it + 0],
				points[2 * it + 1],
			)
		}
	}

	@JvmStatic
	fun triangulation(vararg points: Vector2fc): List<com.greentree.commons.geometry.geom2d.shape.Triangle2D> {
		return triangulation0(points)
	}

	@JvmStatic
	fun triangulation(shape: FiniteShape2D): Collection<com.greentree.commons.geometry.geom2d.shape.Triangle2D> {
		return triangulation(*shape.points)
	}

	@JvmStatic
	fun triangulation(iter: Iterable<Vector2fc>): Collection<com.greentree.commons.geometry.geom2d.shape.Triangle2D> {
		return triangulation(
			*com.greentree.commons.util.iterator.IteratorUtil.array(
				iter, arrayOfNulls(
					com.greentree.commons.util.iterator.IteratorUtil.size(iter)
				)
			)
		)
	}

	fun lastIndexConvex(points: Array<out Vector2fc>): Int {
		val vlines = arrayOfNulls<Vector2fc>(points.size + 1)
		for(i in 0 ..< points.size - 1) {
			val p0 = points[i]
			val p1 = points[i + 1]
			vlines[i] = p1.minus(p0)
		}
		run {
			val p0 = points[points.size - 1]
			val p1 = points[0]
			vlines[vlines.size - 2] = p1.minus(p0)
		}
		run {
			vlines[vlines.size - 1] = vlines[0]
		}
		for(i in vlines.size - 2 downTo 0) {
			val l0 = vlines[i]!!
			val l1 = vlines[i + 1]!!
			if(l0.cross(l1) > 0) return (i + 1) % points.size
		}
		throw RuntimeException("unreal Exception")
	}

	private fun triangulation0(points: Array<out Vector2fc>): List<com.greentree.commons.geometry.geom2d.shape.Triangle2D> {
		val len = points.size
		if(len > 3) {
			val ic = lastIndexConvex(points)
			val `as` = Array(3) {
				points[((it + ic - 1) % points.size + points.size) % points.size]
			}
			val a = triangulation(*`as`)
			val bs = remove(points, ic)
			val b = triangulation(*bs)
			return union(a, b)
		}
		if(len == 3) {
			val p0 = Vector2f(1f, 0f)
			points.sortBy {
				it.minus(
					points[0]
				).normalize(1f).cross(p0)
			}
			return listOf(
				com.greentree.commons.geometry.geom2d.shape.Triangle2D(
					points[0],
					points[1],
					points[2]
				)
			)
		}
		throw UnsupportedOperationException("points.length=" + points.size)
	}

	private fun remove(points: Array<out Vector2fc>, ic: Int): Array<Vector2fc> {
		val res = arrayOfNulls<Vector2fc>(points.size - 1)
		System.arraycopy(points, 0, res, 0, ic)
		System.arraycopy(points, ic + 1, res, ic, points.size - ic - 1)
		return res as Array<Vector2fc>
	}

	private fun union(
		a: List<com.greentree.commons.geometry.geom2d.shape.Triangle2D>,
		b: List<com.greentree.commons.geometry.geom2d.shape.Triangle2D>
	): List<com.greentree.commons.geometry.geom2d.shape.Triangle2D> {
		val res = ArrayList(a)
		res.addAll(b)
		return res
	}
}
