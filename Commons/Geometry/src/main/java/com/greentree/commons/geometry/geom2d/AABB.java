package com.greentree.commons.geometry.geom2d;

import com.greentree.commons.geometry.math.float2;

/** @author Arseny Latyshev */
public class AABB {

	private final static float BORDER_COEFFICIENT = 0.0000001f;

	private final float2 min, max;

	public AABB(float minx, float miny, float w, float h) {
		this(new float2(minx, miny), new float2(minx + w, miny + h));
	}

	public AABB(float2...fs) {
		min = new float2();
		max = new float2();

		max.x = -Float.MAX_VALUE;
		max.y = -Float.MAX_VALUE;
		min.x = Float.MAX_VALUE;
		min.y = Float.MAX_VALUE;

		for(var p : fs) {
			max.x = Math.max(max.x, p.x);
			max.y = Math.max(max.y, p.y);
			min.x = Math.min(min.x, p.x);
			min.y = Math.min(min.y, p.y);
		}
		expand(min, max);
	}

	public AABB(IShape2D shape) {
		min = new float2();
		max = new float2();
		max.x = -Float.MAX_VALUE;
		max.y = -Float.MAX_VALUE;
		min.x = Float.MAX_VALUE;
		min.y = Float.MAX_VALUE;
		for(var p : shape.getPoints()) {
			max.x = Math.max(max.x, p.x());
			max.y = Math.max(max.y, p.y());
			min.x = Math.min(min.x, p.x());
			min.y = Math.min(min.y, p.y());
		}
		expand(min, max);
	}

	private static void expand(float2 min, float2 max) {
		float dd = (max.x - min.x + max.y - min.y + 1) * BORDER_COEFFICIENT;
		min.y -= dd;
		max.y += dd;
		min.x -= dd;
		max.x += dd;
	}

	public float getHeight() {
		return max.y - min.y;
	}

	public float getMaxX() {
		return max.x;
	}

	public float getMaxY() {
		return max.y;
	}

	public float getMediunX() {
		return (max.x + min.x) / 2f;
	}

	public float getMediunY() {
		return (max.y + min.y) / 2f;
	}

	public float getMinX() {
		return min.x;
	}

	public float getMinY() {
		return min.y;
	}

	public float getWidth() {
		return max.x - min.x;
	}

	public boolean isIntersect(AABB other) {
		return max.x >= other.min.x && min.x <= other.max.x && max.y >= other.min.y && min.y <= other.max.y;
	}

	public boolean isIntersect(float x, float y) {
		return max.x >= x && min.x <= x && max.y >= y && min.y <= y;
	}

	@Override
	public String toString() {
		return "AABB [min=" + min + ", max=" + max + "]";
	}

}
