package com.greentree.commons.geometry.geom2d.atomic;

import com.greentree.commons.math.MathLine1D;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.Vector2f;
import com.greentree.commons.math.vector.Vector3f;
import com.greentree.commons.util.iterator.IteratorUtil;
import org.joml.Matrix2f;
import org.joml.Matrix3f;

public interface SimpleShape2D {

    default MathLine1D getProjection(Matrix3f model, AbstractVector2f normal) {
        var v = normal.times(new Matrix2f(model));
        var len = v.length();
        v = v.normalize(1);
        var ml = getProjection(v);
        return new MathLine1D(ml.min * len, ml.max * len);
    }

    MathLine1D getProjection(AbstractVector2f normal);

    default boolean isInsade(Matrix3f model, AbstractVector2f point) {
        var invModelMatrix = new Matrix3f();
        model.invert(invModelMatrix);
        final var v = new Vector3f(point, 1).times(invModelMatrix);
        return isInsade(v.xy());
    }

    boolean isInsade(AbstractVector2f point);

    default AbstractVector2f min(Matrix3f model, AbstractVector2f point) {
        var invModelMatrix = new Matrix3f();
        model.invert(invModelMatrix);
        final var v = new Vector3f(point, 1).times(invModelMatrix);
        return new Vector3f(min(v.xy()), 1).times(model).xy();
    }

    AbstractVector2f min(AbstractVector2f point);

    default Iterable<? extends AbstractVector2f> normals(Matrix3f model) {
        return IteratorUtil.map(normals(), n -> new Vector2f(n).times(new Matrix2f(model)));
    }

    Iterable<? extends AbstractVector2f> normals();

    default Iterable<? extends AbstractVector2f> points(Matrix3f model) {
        return IteratorUtil.map(points(), n -> new Vector3f(n, 1).times(model).xy());
    }

    Iterable<? extends AbstractVector2f> points();

}
