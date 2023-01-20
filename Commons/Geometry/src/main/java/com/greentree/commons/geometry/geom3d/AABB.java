package com.greentree.commons.geometry.geom3d;

import java.util.function.Consumer;

import com.greentree.commons.math.float3;
import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.commons.math.vector.Vector3f;

/** @author Arseny Latyshev */
public class AABB {
	
	private final static float BORDER_COEFFICIENT = 0.0000001f;
	
	private final float3 min, max;
	
	public AABB(AbstractVector3f... points) {
		min = new float3();
		max = new float3();
		max.x = -Float.MAX_VALUE;
		max.y = -Float.MAX_VALUE;
		max.z = -Float.MAX_VALUE;
		min.x = Float.MAX_VALUE;
		min.y = Float.MAX_VALUE;
		min.z = Float.MAX_VALUE;
		for(var p : points) {
			max.x = Math.max(max.x, p.x());
			max.y = Math.max(max.y, p.y());
			max.z = Math.max(max.z, p.z());
			min.x = Math.min(min.x, p.x());
			min.y = Math.min(min.y, p.y());
			min.z = Math.min(min.z, p.z());
		}
		expand();
	}
	
	public AABB(float minx, float miny, float minz, float maxx, float maxy, float maxz) {
		this(new Vector3f(minx, miny, minz), new Vector3f(maxx, maxy, maxz));
	}
	
	public AABB(IShape3D shape) {
		this(shape.getPoints());
	}
	
	public AABB get(Consumer<AbstractVector3f> mod) {
		
		AbstractVector3f[] fs = new AbstractVector3f[8];
		
		fs[0] = new Vector3f(min.x, min.y, min.z);
		fs[1] = new Vector3f(min.x, min.y, max.z);
		fs[2] = new Vector3f(min.x, max.y, min.z);
		fs[3] = new Vector3f(min.x, max.y, max.z);
		fs[4] = new Vector3f(max.x, min.y, min.z);
		fs[5] = new Vector3f(max.x, min.y, max.z);
		fs[6] = new Vector3f(max.x, max.y, min.z);
		fs[7] = new Vector3f(max.x, max.y, max.z);
		
		for(var f : fs)
			mod.accept(f);
		
		return new AABB(fs);
	}
	
	public float getMaxX() {
		return max.x;
	}
	
	public float getMaxY() {
		return max.y;
	}
	
	public float getMaxZ() {
		return max.z;
	}
	
	public AbstractVector3f getMedium() {
		return new Vector3f(getMediumX(), getMediumY(), getMediumZ());
	}
	
	public float getMediumX() {
		return (max.x + min.x) / 2f;
	}
	
	public float getMediumY() {
		return (max.y + min.y) / 2f;
	}
	
	public float getMediumZ() {
		return (max.z + min.z) / 2f;
	}
	
	public float getMinX() {
		return min.x;
	}
	
	public float getMinY() {
		return min.y;
	}
	
	public float getMinZ() {
		return min.z;
	}
	
	public AbstractVector3f getOverlay() {
		return new Vector3f(getOverlayX(), getOverlayY(), getOverlayZ());
	}
	
	public float getOverlayX() {
		return max.x - min.x;
	}
	
	public float getOverlayY() {
		return max.y - min.y;
	}
	
	public float getOverlayZ() {
		return max.z - min.z;
	}
	
	public boolean isIntersect(AABB other) {
		return max.x >= other.min.x && min.x <= other.max.x && max.y >= other.min.y
				&& min.y <= other.max.y && max.z >= other.min.z && min.z <= other.max.z;
	}
	
	public boolean isIntersect(float x, float y, float z) {
		return max.x >= x && min.x <= x && max.y >= y && min.y <= y && max.z >= z && min.z <= z;
	}
	
	@Override
	public String toString() {
		return "AABB [min=" + min + ", max=" + max + "]";
	}
	
	private final void expand() {
		float dd = (getOverlayX() + getOverlayY() + getOverlayZ() + 1) * BORDER_COEFFICIENT;
		min.y -= dd;
		max.y += dd;
		min.x -= dd;
		max.x += dd;
		min.z -= dd;
		max.z += dd;
	}
	
}
