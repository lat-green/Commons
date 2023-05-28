package com.greentree.commons.geometry.geom2d.shape;

import org.joml.Math;
import org.joml.Matrix3f;

import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.Vector2f;
import com.greentree.commons.math.vector.Vector3f;

public interface Transform2D {
	
	default void get(AbstractVector2f[] points, AbstractVector2f[] result) {
		var mat = getModelMatrix();
		var temp3 = new Vector3f();
		for(int i = 0; i < points.length; i++) {
			temp3.set(points[i], 1);
			result[i] = temp3.times(mat).xy();
		}
	}
	
	default AbstractVector2f get(Vector2f point) {
		var mat = getModelMatrix();
		return new Vector3f(point, 1).times(mat).xy();
	}
	
	default Matrix3f getModelMatrix() {
		float angle = getRotation();
		var scale = getScale();
		var position = getPosition();
		
		Matrix3f mat3 = new Matrix3f().identity();
		
		float s = Math.sin(angle);
		float c = Math.cosFromSin(s, angle);
		
		mat3.m00 = c * scale.x();
		mat3.m01 = s * scale.y();
		mat3.m10 = -s * scale.x();
		mat3.m11 = c * scale.y();
		
		mat3.m20 = position.x();
		mat3.m21 = position.y();
		return mat3;
	}
	
	AbstractVector2f getPosition();
	float getRotation();
	AbstractVector2f getScale();
	
	default boolean setPosition(AbstractVector2f vec) {
		return setPosition(vec.x(), vec.y());
	}
	
	boolean setPosition(float x, float y);
	
	boolean setRotation(double angle);
	
	default boolean setScale(AbstractVector2f vec) {
		return setScale(vec.x(), vec.y());
	}
	
	boolean setScale(float x, float y);
	
}
