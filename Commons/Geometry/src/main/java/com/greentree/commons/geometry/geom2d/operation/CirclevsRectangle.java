package com.greentree.commons.geometry.geom2d.operation;

import com.greentree.commons.geometry.geom2d.collision.CollisionEvent2D;
import com.greentree.commons.geometry.geom2d.shape.Circle2D;
import com.greentree.commons.geometry.geom2d.shape.Rectangle2D;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.Vector2f;

import static com.greentree.commons.math.vector.AbstractVector2fKt.vec2f;

public class CirclevsRectangle extends Shape2DBinaryOperation<Circle2D, Rectangle2D> {

    @Override
    public CollisionEvent2D.Builder getCollisionEvent(Circle2D a, Rectangle2D b) {
        var circle = b.getBoundingCircle();
        float x_delta = circle.getCenter().x() - a.getCenter().x();
        float y_delta = circle.getCenter().y() - a.getCenter().y();
        // Ближайшая к центру B точка A
        Vector2f closest = new Vector2f(x_delta, y_delta);
        // Вычисление половины ширины вдоль каждой оси
        float x_extent = b.getWidth() / 2;
        float y_extent = b.getHeight() / 2;
        // Ограничиваем точку ребром AABB
        closest.setX(Mathf.clamp(-x_extent, x_extent, closest.getX()));
        closest.setY(Mathf.clamp(-y_extent, y_extent, closest.getY()));
        boolean inside = false;
        // Окружность внутри AABB, поэтому нам нужно ограничить центр окружности
        // до ближайшего ребра
        if (closest.equals(new Vector2f(x_delta, y_delta))) {
            inside = true;
            float x_delta_abs = Mathf.abs(x_delta);
            float y_delta_abs = Mathf.abs(y_delta);
            // Находим ближайшую ось
            if (x_delta_abs > y_delta_abs) {
                // Отсекаем до ближайшей ширины
                if (closest.getX() > 0)
                    closest.setX(x_extent);
                else
                    closest.setX(-x_extent);
            } else // Отсекаем до ближайшей ширины
                if (closest.getY() > 0)
                    closest.setY(y_extent);
                else
                    closest.setY(-y_extent);
        }
        var normal = closest.plus(vec2f(x_delta, y_extent));
        float d = normal.length();
        float r = a.getRadius();
        // Если окружность была внутри AABB, то нормаль коллизии нужно отобразить
        // в точку снаружи
        if (inside)
            normal = normal.times(-1);
        return new CollisionEvent2D.Builder(new Vector2f(), normal, r - d);
    }

}
