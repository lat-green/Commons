package com.greentree.commons.geometry.geom2d.shape;

import com.greentree.commons.geometry.geom2d.util.Transform2DImpl;
import com.greentree.commons.geometry.geom2d.util.VectorGeometryUtil;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.FinalVector2f;

public final class Ellipse2D extends Shape2D {

    private final static FinalVector2f[] POINTS = VectorGeometryUtil.getUnitCirclePoints();

    public Ellipse2D(float radius) {
        this();
        scale(radius);
    }

    public Ellipse2D() {
        super(new Transform2DImpl(), POINTS);
    }

    public Ellipse2D(float radiusx, float radiusy) {
        this();
        scale(radiusx, radiusy);
    }

    @Override
    public Ellipse2D clone() {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getArea() {
        var scale = getTransform().getScale();
        return scale.x() * scale.y() * Mathf.PI;
    }

    @Override
    public float getPerimeter() {
        var scale = getTransform().getScale();
        float d = scale.x() - scale.y();
        return 4 * (Mathf.PI * scale.x() * scale.y() + d * d) / (scale.x() + scale.y());
    }

    public float getRadius() {
        var scale = getTransform().getScale();
        return Mathf.max(scale.x(), scale.y());
    }

    @Override
    public boolean isInside(AbstractVector2f p) {
        var scale = getTransform().getScale();
        var pos = getTransform().getPosition();
        float a = scale.x();
        float b = scale.y();
        float x = pos.x() - p.x();
        float y = pos.y() - p.y();
        return x * x / a / a + y * y / b / b <= 1;
    }

}
