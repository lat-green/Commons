package com.greentree.commons.geometry.geom2d.operation

import com.greentree.commons.geometry.geom2d.collision.CollisionEvent2D
import com.greentree.commons.geometry.geom2d.shape.Circle2D
import com.greentree.commons.geometry.geom2d.shape.Rectangle2D
import com.greentree.commons.math.Mathf.abs
import com.greentree.commons.math.Mathf.clamp
import com.greentree.commons.math.setX
import com.greentree.commons.math.setY
import com.greentree.commons.math.vec2f
import org.joml.Vector2f
import org.joml.plus
import org.joml.unaryMinus

class CircleVsRectangle : Shape2DBinaryOperation<Circle2D, Rectangle2D>() {

	override fun getCollisionEvent(a: Circle2D, b: Rectangle2D): CollisionEvent2D.Builder {
		val circle = b.boundingCircle
		val x_delta = circle.center.x() - a.center.x()
		val y_delta = circle.center.y() - a.center.y()
		// Ближайшая к центру B точка A
		val closest = Vector2f(x_delta, y_delta)
		// Вычисление половины ширины вдоль каждой оси
		val x_extent = b.width / 2
		val y_extent = b.height / 2
		// Ограничиваем точку ребром AABB
		closest[clamp(-x_extent, x_extent, closest.x())] = clamp(-y_extent, y_extent, closest.y())
		var inside = false
		// Окружность внутри AABB, поэтому нам нужно ограничить центр окружности
		// до ближайшего ребра
		if(closest == Vector2f(x_delta, y_delta)) {
			inside = true
			val x_delta_abs = abs(x_delta)
			val y_delta_abs = abs(y_delta)
			// Находим ближайшую ось
			if(x_delta_abs > y_delta_abs) {
				// Отсекаем до ближайшей ширины
				if(closest.x() > 0) closest.setX(x_extent)
				else closest.setX(-x_extent)
			} else  // Отсекаем до ближайшей ширины
				if(closest.y() > 0) closest.setY(y_extent)
				else closest.setY(-y_extent)
		}
		var normal = closest.plus(vec2f(x_delta, y_extent))
		val d: Float = normal.length()
		val r = a.radius
		// Если окружность была внутри AABB, то нормаль коллизии нужно отобразить
		// в точку снаружи
		if(inside)
			normal = -normal
		return CollisionEvent2D.Builder(Vector2f(), normal, r - d)
	}
}
