package com.greentree.commons.math;

import java.util.function.Function;

import org.joml.Matrix2f;
import org.joml.Matrix3f;

import com.greentree.commons.math.vector.AbstractVector3f;

/**
 * @author Arseny Latyshev
 *
 */
public final class Mathf {

	public static final float PI = (float) Math.PI;
	public static final float PI2 = 2 * PI;
	public static final float PIHalf = PI / 2;
	public static final float EPS = 1E-9f;

	public static final float PI_INV = 1.0f / PI;

	public static final float sqrt2 = 1.41421356237f;
	public static final float sqrt3 = 1.73205080757f;
	public static final float E = (float) Math.E;

	private Mathf() {
	}

    public static int abs(int n) {
        int mask = n >> (8 * Byte.SIZE - 1);
        return ((n + mask) ^ mask);
    }
	
	public static float angleRadian(float angle) {
		return angle * PI / 180;
	}
	public static float abs(float x) {
		if(x > 0)
			return x;
		return -x;
	}
	public static float acos(double a) {
		return (float) Math.acos(a);
	}

	public static float acos(float a) {
		return (float) Math.acos(a);
	}

	public static float atan(double x) {
		return (float) Math.atan(x);
	}

	public static float atan(float x) {
		return (float) Math.atan(x);
	}

	public static float clamp(float min, float max, float value) {
		if(value <= min)return min;
		if(value >= max)return max;
		return value;
	}
	public static float cos(double d) {
		return (float)Math.cos(d);
	}
	public static float cos(float d) {
		return (float)Math.cos(d);
	}



	public static float cos(float angle, float sin) {
		// sin(x)^2 + cos(x)^2 = 1
		float cos = sqrt(1.0f - sin * sin);
		angle += PIHalf;
		angle %= Mathf.PI2;
		angle += Mathf.PI2;
		angle %= Mathf.PI2;
		if(angle < PI)
			return cos;
		else
			return -cos;
	}

	public static boolean equals(float a, float b){
		return abs(a - b) < EPS;
	}

	public static int get2Fold(final float fold) {
		return getFold(fold, 2);
	}

	public static float getFold(final float fold, final float pow) {
		float ret = 1;
		while(ret < fold) ret *= pow;
		return ret;
	}
	public static int getFold(final float fold, final int pow) {
		int ret = 1;
		while(ret < fold) ret *= pow;
		return ret;
	}

	public static float[] getPoints(float x, float y, float w, float h) {
		return new float[] {x, y, x, y+h, x+w, y+h, x+w, y};
	}

	public static boolean isCosPositive(float angle) {
		return isSinPositive(angle + PIHalf);
	}

	public static boolean isSinPositive(float angle) {
		angle %= PI2;
		return angle < PI;
	}
	public static float lerp(double a, double b, double k) {
		return (float) ((b - a)*k + a);
	}

	public static float lerp(float a, float b) {
		return (b - a) * Mathf.random() + a;
	}
	public static float lerp2(double a, double b, double ak, double bk) {
		return lerp(a, b, ak/(ak + bk));
	}

	public static float max(double...ds) {
		if(ds.length == 0)throw new IllegalArgumentException("array is empty");
		double max = ds[0];
		for(int i = 1; i < ds.length; i++)if(max < ds[i])max = ds[i];
		return (float) max;
	}

	public static float max(float a, float b) {
		return a > b ? a : b;
	}
	public static <E> float max(Iterable<E> collection, Function<E, Float> function) {
		float max = Float.MIN_VALUE;
		for(E a : collection) {
			float c = function.apply(a);
			if(max < c) max = c;
		}
		return max;
	}

	/**
	 * @return element in collection with max cast
	 */
	@SafeVarargs
	public static <E> float max(Function<E, Float> function, E...collection) {
		float max = -Float.MAX_VALUE;
		for(E a : collection) {
			float c = function.apply(a);
			if(max < c) max = c;
		}
		return max;
	}
	/**
	 * @return element in collection with max cast
	 */
	@SafeVarargs
	public static <E> E maxElement(Function<E, Float> function, E...collection) {
		float max = -Float.MAX_VALUE;
		E e = null;
		for(E a : collection) {
			float c = function.apply(a);
			if(max < c){
				max = c;
				e = a;
			}
		}
		return e;
	}

	/**
	 * @return element in collection with max cast
	 */
	public static <E> E maxElement(Iterable<E> collection, Function<E, Float> function) {
		float max = -Float.MAX_VALUE;
		E e = null;
		for(E a : collection) {
			float c = function.apply(a);
			if(max < c){
				max = c;
				e = a;
			}
		}
		return e;
	}

	public static AbstractVector3f maxElement(Object object, AbstractVector3f[] points) {
		// TODO Auto-generated method stub
		return null;
	}
	public static float min(double...ds) {
		if(ds.length == 0)throw new IllegalArgumentException("array is empty");
		double min = ds[0];
		for(int i = 1; i < ds.length; i++)if(min > ds[i])min = ds[i];
		return (float) min;
	}

	public static float min(float a, float b) {
		return a < b ? a : b;
	}
	@SafeVarargs
	public static <E> float min(Function<E, Float> function, E...collection) {
		float min = Float.MAX_VALUE;
		for(E a : collection) {
			float c = function.apply(a);
			if(min > c) min = c;
		}
		return min;
	}
	public static <E> float min(Iterable<E> collection, Function<E, Float> function) {
		float min = Float.MAX_VALUE;
		for(E a : collection) {
			float c = function.apply(a);
			if(min > c) min = c;
		}
		return min;
	}
	/**
	 * @return element in collection with min cast
	 */
	@SafeVarargs
	public static <E> E minElement(Function<E, Float> function, E...collection) {
		float min = Float.MAX_VALUE;
		E e = null;
		for(E a : collection) {
			float c = function.apply(a);
			if(min > c){
				min = c;
				e = a;
			}
		}
		return e;
	}
	/**
	 * @return element in collection with min cast
	 */
	public static <E> E minElement(Iterable<E> collection, Function<E, Float> function) {
		float min = Float.MAX_VALUE;
		E e = null;
		for(E a : collection) {
			float c = function.apply(a);
			if(min > c){
				min = c;
				e = a;
			}
		}
		return e;
	}

	/**
	 * @return element in collection with min cast
	 */
	@SafeVarargs
	public static <E> int minIndex(Function<E, Float> function, E...collection) {
		int index = -1;
		float min = Float.MAX_VALUE;
		for(int i = 0; i < collection.length; i++) {
			var a = collection[i];
			float c = function.apply(a);
			if(min > c) {
				index = i;
				min = c;
			}
		}
		return index;
	}
	public static float random() {
		return (float)Math.random();
	}

	public static Matrix2f rot2(float a) {
		float s = sin(a);
		float c = cos(a, s);
		return new Matrix2f( c, s,
				-s, c);
	}

	public static Matrix3f rotXZ(float a) {
		float s = sin(a);
		float c = cos(a, s);
		return new Matrix3f(c, 0, s,
				0, 1, 0,
				-s, 0, c);
	}

	public static Matrix3f rotYX(float a) {
		float s = sin(a);
		float c = cos(a, s);
		return new Matrix3f(c, -s, 0,
				s,  c, 0,
				0,  0, 1);
	}
	public static Matrix3f rotZY(float a) {
		float s = sin(a);
		float c = cos(a, s);
		return new Matrix3f(1,  0, 0,
				0, c, -s,
				0, s,  c);
	}

	public static float sign(float x) {
		if(equals(x, 0))return 1;
		float abs = abs(x);
		return x / abs;
	}
	public static float sin(double d) {
		return (float)Math.sin(d);
	}

	public static float sin(float d) {
		return (float)Math.sin(d);
	}

	public static float sin(float angle, float cos) {
		// sin(x)^2 + cos(x)^2 = 1
		float sin = sqrt(1.0f - cos * cos);

		angle %= Mathf.PI2;
		angle += Mathf.PI2;
		angle %= Mathf.PI2;

		if(angle < Mathf.PI)
			return sin;
		else
			return -sin;
	}

	public static float sqrt(double d) {
		return (float)Math.sqrt(d);
	}

	public static float sqrt(float d) {
		return (float)Math.sqrt(d);
	}

	public static float tan(double x) {
		return (float) Math.tan(x);
	}

	public static float tan(float x) {
		return (float) Math.tan(x);
	}

	public static float pow(double x, double power) {
		return (float) Math.pow(x, power);
	}


}
