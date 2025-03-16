package com.greentree.commons.math

import org.joml.Matrix2f
import org.joml.Matrix3f
import java.util.function.Function

/**
 * @author Arseny Latyshev
 */
object Mathf {

	const val PI = Math.PI.toFloat()
	const val PI2 = 2 * PI
	const val PIHalf = PI / 2
	const val EPS = 1E-9f
	const val PI_INV = 1.0f / PI
	const val sqrt2 = 1.41421356237f
	const val sqrt3 = 1.73205080757f
	const val E = Math.E.toFloat()

	@JvmStatic
	fun abs(n: Int): Int {
		val mask = n shr 8 * java.lang.Byte.SIZE - 1
		return n + mask xor mask
	}

	@JvmStatic
	fun angleRadian(angle: Float): Float {
		return angle * PI / 180
	}

	@JvmStatic
	fun abs(x: Float): Float {
		return if(x > 0) x else -x
	}

	@JvmStatic
	fun acos(a: Double): Float {
		return Math.acos(a).toFloat()
	}

	@JvmStatic
	fun acos(a: Float): Float {
		return Math.acos(a.toDouble()).toFloat()
	}

	@JvmStatic
	fun atan(x: Double): Float {
		return Math.atan(x).toFloat()
	}

	@JvmStatic
	fun atan(x: Float): Float {
		return Math.atan(x.toDouble()).toFloat()
	}

	@JvmStatic
	fun clamp(min: Float, max: Float, value: Float): Float {
		if(value <= min) return min
		return if(value >= max) max else value
	}

	@JvmStatic
	fun cos(d: Double): Float {
		return Math.cos(d).toFloat()
	}

	@JvmStatic
	fun cos(d: Float): Float {
		return Math.cos(d.toDouble()).toFloat()
	}

	@JvmStatic
	fun cos(angle: Float, sin: Float): Float {
		// sin(x)^2 + cos(x)^2 = 1
		var angle = angle
		val cos = sqrt(1.0f - sin * sin)
		angle += PIHalf
		angle %= PI2
		angle += PI2
		angle %= PI2
		return if(angle < PI) cos else -cos
	}

	@JvmStatic
	fun equals(a: Float, b: Float): Boolean {
		return abs(a - b) < EPS
	}

	@JvmStatic
	fun get2Fold(fold: Float): Int {
		return getFold(fold, 2)
	}

	@JvmStatic
	fun getFold(fold: Float, pow: Float): Float {
		var ret = 1f
		while(ret < fold) ret *= pow
		return ret
	}

	@JvmStatic
	fun getFold(fold: Float, pow: Int): Int {
		var ret = 1
		while(ret < fold) ret *= pow
		return ret
	}

	@JvmStatic
	fun getPoints(x: Float, y: Float, w: Float, h: Float): FloatArray {
		return floatArrayOf(x, y, x, y + h, x + w, y + h, x + w, y)
	}

	@JvmStatic
	fun isCosPositive(angle: Float): Boolean {
		return isSinPositive(angle + PIHalf)
	}

	@JvmStatic
	fun isSinPositive(angle: Float): Boolean {
		var angle = angle
		angle %= PI2
		return angle < PI
	}

	@JvmStatic
	fun lerp(a: Double, b: Double, k: Double): Float {
		return ((b - a) * k + a).toFloat()
	}

	@JvmStatic
	fun lerp(a: Float, b: Float, k: Float): Float {
		return (b - a) * k + a
	}

	@JvmStatic
	fun lerp(a: Float, b: Float): Float {
		return (b - a) * random() + a
	}

	@JvmStatic
	fun lerp2(a: Double, b: Double, ak: Double, bk: Double): Float {
		return lerp(a, b, ak / (ak + bk))
	}

	@JvmStatic
	fun max(vararg ds: Double): Float {
		require(ds.size != 0) { "array is empty" }
		var max = ds[0]
		for(i in 1 until ds.size) if(max < ds[i]) max = ds[i]
		return max.toFloat()
	}

	@JvmStatic
	fun max(a: Float, b: Float): Float {
		return if(a > b) a else b
	}

	@JvmStatic
	fun <E> max(collection: Iterable<E>, function: Function<E, Float>): Float {
		var max = Float.MIN_VALUE
		for(a in collection) {
			val c = function.apply(a)
			if(max < c) max = c
		}
		return max
	}

	/**
	 * @return element in collection with max cast
	 */
	@SafeVarargs
	@JvmStatic
	fun <E> max(function: Function<E, Float>, vararg collection: E): Float {
		var max = -Float.MAX_VALUE
		for(a in collection) {
			val c = function.apply(a)
			if(max < c) max = c
		}
		return max
	}

	/**
	 * @return element in collection with max cast
	 */
	@SafeVarargs
	@JvmStatic
	fun <E> maxElement(function: Function<E, Float>, vararg collection: E): E? {
		var max = -Float.MAX_VALUE
		var e: E? = null
		for(a in collection) {
			val c = function.apply(a)
			if(max < c) {
				max = c
				e = a
			}
		}
		return e
	}

	/**
	 * @return element in collection with max cast
	 */
	@JvmStatic
	fun <E> maxElement(collection: Iterable<E>, function: Function<E, Float>): E? {
		var max = -Float.MAX_VALUE
		var e: E? = null
		for(a in collection) {
			val c = function.apply(a)
			if(max < c) {
				max = c
				e = a
			}
		}
		return e
	}

	@JvmStatic
	fun min(vararg ds: Double): Float {
		require(ds.size != 0) { "array is empty" }
		var min = ds[0]
		for(i in 1 until ds.size) if(min > ds[i]) min = ds[i]
		return min.toFloat()
	}

	@JvmStatic
	fun min(a: Float, b: Float): Float {
		return if(a < b) a else b
	}

	@JvmStatic
	@SafeVarargs
	fun <E> min(function: Function<E, Float>, vararg collection: E): Float {
		var min = Float.MAX_VALUE
		for(a in collection) {
			val c = function.apply(a)
			if(min > c) min = c
		}
		return min
	}

	@JvmStatic
	fun <E> min(collection: Iterable<E>, function: Function<E, Float>): Float {
		var min = Float.MAX_VALUE
		for(a in collection) {
			val c = function.apply(a)
			if(min > c) min = c
		}
		return min
	}

	/**
	 * @return element in collection with min cast
	 */
	@JvmStatic
	@SafeVarargs
	fun <E> minElement(function: Function<E, Float>, vararg collection: E): E? {
		var min = Float.MAX_VALUE
		var e: E? = null
		for(a in collection) {
			val c = function.apply(a)
			if(min > c) {
				min = c
				e = a
			}
		}
		return e
	}

	@JvmStatic
			/**
			 * @return element in collection with min cast
			 */
	fun <E> minElement(collection: Iterable<E>, function: Function<E, Float>): E? {
		var min = Float.MAX_VALUE
		var e: E? = null
		for(a in collection) {
			val c = function.apply(a)
			if(min > c) {
				min = c
				e = a
			}
		}
		return e
	}

	@JvmStatic
	/**
	 * @return element in collection with min cast
	 */
	@SafeVarargs
	fun <E> minIndex(function: (E) -> Float, vararg collection: E): Int {
		var index = -1
		var min = Float.MAX_VALUE
		for(i in collection.indices) {
			val a = collection[i]
			val c = function(a)
			if(min > c) {
				index = i
				min = c
			}
		}
		return index
	}

	@JvmStatic
	fun random(): Float {
		return Math.random().toFloat()
	}

	@JvmStatic
	fun rot2(a: Float): Matrix2f {
		val s = sin(a)
		val c = cos(a, s)
		return Matrix2f(
			c, s,
			-s, c
		)
	}

	@JvmStatic
	fun rotXZ(a: Float): Matrix3f {
		val s = sin(a)
		val c = cos(a, s)
		return Matrix3f(
			c, 0f, s,
			0f, 1f, 0f,
			-s, 0f, c
		)
	}

	@JvmStatic
	fun rotYX(a: Float): Matrix3f {
		val s = sin(a)
		val c = cos(a, s)
		return Matrix3f(
			c, -s, 0f,
			s, c, 0f,
			0f, 0f, 1f
		)
	}

	@JvmStatic
	fun rotZY(a: Float): Matrix3f {
		val s = sin(a)
		val c = cos(a, s)
		return Matrix3f(
			1f, 0f, 0f,
			0f, c, -s,
			0f, s, c
		)
	}

	@JvmStatic
	fun sign(x: Float): Float {
		if(equals(x, 0f))
			return 1f
		val abs = abs(x)
		return x / abs
	}

	@JvmStatic
	fun sin(d: Double): Float {
		return Math.sin(d).toFloat()
	}

	@JvmStatic
	fun sin(d: Float): Float {
		return Math.sin(d.toDouble()).toFloat()
	}

	@JvmStatic
	fun sin(angle: Float, cos: Float): Float {
		// sin(x)^2 + cos(x)^2 = 1
		var angle = angle
		val sin = sqrt(1.0f - cos * cos)
		angle %= PI2
		angle += PI2
		angle %= PI2
		return if(angle < PI) sin else -sin
	}

	@JvmStatic
	fun sqrt(d: Double): Float {
		return Math.sqrt(d).toFloat()
	}

	@JvmStatic
	fun sqrt(d: Float): Float {
		return Math.sqrt(d.toDouble()).toFloat()
	}

	@JvmStatic
	fun tan(x: Double): Float {
		return Math.tan(x).toFloat()
	}

	@JvmStatic
	fun tan(x: Float): Float {
		return Math.tan(x.toDouble()).toFloat()
	}

	@JvmStatic
	fun pow(x: Double, power: Double): Float {
		return Math.pow(x, power).toFloat()
	}

	@JvmStatic
	fun atan2(sin: Float, cos: Float): Float {
		return Math.atan2(sin.toDouble(), cos.toDouble()).toFloat()
	}
}