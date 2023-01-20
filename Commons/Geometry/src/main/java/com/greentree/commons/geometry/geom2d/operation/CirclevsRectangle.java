package com.greentree.commons.geometry.geom2d.operation;

import com.greentree.commons.geometry.geom2d.AABB;
import com.greentree.commons.geometry.geom2d.collision.CollisionEvent2D;
import com.greentree.commons.geometry.geom2d.shape.Circle2D;
import com.greentree.commons.geometry.geom2d.shape.Rectangle2D;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.Vector2f;

public class CirclevsRectangle extends Shape2DBinaryOperation<Circle2D, Rectangle2D> {
	
	@Override
	public CollisionEvent2D.Builder getCollisionEvent(Circle2D a, Rectangle2D b) {
		
		AABB box = b.getAABB();
		
		float x_delta = box.getMediunX() - a.getCenter().x();
		float y_delta = box.getMediunY() - b.getCenter().y();
		
		// Ближайшая к центру B точка A
		Vector2f closest = new Vector2f(x_delta, y_delta);
		
		// Вычисление половины ширины вдоль каждой оси
		float x_extent = box.getWidth() / 2;
		float y_extent = box.getHeight() / 2;
		
		// Ограничиваем точку ребром AABB
		closest.x = Mathf.clamp(-x_extent, x_extent, closest.x);
		closest.y = Mathf.clamp(-y_extent, y_extent, closest.y);
		
		boolean inside = false;
		
		// Окружность внутри AABB, поэтому нам нужно ограничить центр окружности
		// до ближайшего ребра
		if(closest.equals(new Vector2f(x_delta, y_delta))) {
			inside = true;
			
			float x_delta_abs = Mathf.abs(x_delta);
			float y_delta_abs = Mathf.abs(y_delta);
			
			// Находим ближайшую ось
			if(x_delta_abs > y_delta_abs) {
				// Отсекаем до ближайшей ширины
				if(closest.x > 0)
					closest.x = x_extent;
				else
					closest.x = -x_extent;
			}else // Отсекаем до ближайшей ширины
				if(closest.y > 0)
					closest.y = y_extent;
				else
					closest.y = -y_extent;
		}
		
		Vector2f normal = closest.add(x_delta, y_extent, new Vector2f());
		float d = normal.length();
		float r = a.getRadius();
		
		// Если окружность была внутри AABB, то нормаль коллизии нужно отобразить
		// в точку снаружи
		if(inside)
			normal.mul(-1);
		
		return new CollisionEvent2D.Builder(new Vector2f(), normal, r - d);
	}
	
}
