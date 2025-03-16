package com.greentree.commons.geometry.geom2d.operation

import com.greentree.commons.geometry.geom2d.shape.Rectangle2D

class RectangleVsRectangle : Shape2DBinaryOperation<Rectangle2D, Rectangle2D>() {

	override fun isIntersect(a: Rectangle2D, b: Rectangle2D): Boolean {
		return a.maxX >= b.minX && a.minX <= b.maxX && a.maxY >= b.minY && a.minY <= b.maxY
	}
	/*
	@Override
	public CollisionEvent2D<Shape2D> getCollisionEvent(Rectangle a, Rectangle b) {



		AABB abox = a.getAABB();
		AABB bbox = b.getAABB();

		float x_delta = bbox.getMediunX() - abox.getMediunX();
		float y_delta = bbox.getMediunY() - abox.getMediunY();
		float x_delta_abs = Mathf.abs(x_delta);
		float y_delta_abs = Mathf.abs(y_delta);

		float a_x_extent = (abox.getWidth()) / 2;
		float b_x_extent = (bbox.getWidth()) / 2;

		// Вычисление наложения по оси x
		float x_overlap = a_x_extent + b_x_extent - x_delta_abs;

		// Вычисление половины ширины вдоль оси y для каждого объекта
		float a_y_extent = (abox.getHeight()) / 2;
		float b_y_extent = (bbox.getHeight()) / 2;

		// Вычисление наложения по оси y
		float y_overlap = a_y_extent + b_y_extent - y_delta_abs;

		if(x_overlap < y_overlap)
			return new CollisionEvent2D<>(a, b, new Point2D(a.getCenter(), x_delta/2, y_delta/2), new Vector2f(x_delta/x_delta_abs, 0), x_overlap);
		else
			return new CollisionEvent2D<>(a, b, new Point2D(a.getCenter(), x_delta/2, y_delta/2), new Vector2f(0, y_delta/y_delta_abs), y_overlap);

	}
	 */
}
