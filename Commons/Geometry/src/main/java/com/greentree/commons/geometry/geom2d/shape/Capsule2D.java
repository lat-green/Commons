package com.greentree.commons.geometry.geom2d.shape;

import com.greentree.commons.geometry.geom2d.util.Transform2DImpl;
import com.greentree.commons.math.vector.AbstractMutableVector2f;
import com.greentree.commons.math.vector.AbstractVector2f;
import org.joml.Matrix2f;

import static com.greentree.commons.geometry.geom2d.util.VectorGeometryUtil.POINT_IN_CIRCLE;
import static com.greentree.commons.math.vector.AbstractVector2fKt.vec2f;

public final class Capsule2D extends Shape2D {

    public Capsule2D(AbstractVector2f p1, AbstractVector2f p2, float radius) {
        super(new Transform2DImpl(), points(p1, p2, radius));
    }

    public static AbstractVector2f[] points(AbstractVector2f focus1, AbstractVector2f focus2,
                                            float radius) {
        AbstractMutableVector2f init_focus_vec = focus2.minus(focus1).toMutable();// 1 -> 2
        if (init_focus_vec.length() == 0)
            init_focus_vec.set(1f, 0f);
        var focus_vec = vec2f(init_focus_vec);
        focus_vec = focus_vec.times(new Matrix2f().rotate((float) (Math.PI / 2))).normalize(radius);
        final AbstractVector2f[] points = new AbstractVector2f[POINT_IN_CIRCLE];
        final Matrix2f mat = new Matrix2f().rotate((float) (2 * Math.PI / POINT_IN_CIRCLE));
        for (int i = (int) Math.floor(POINT_IN_CIRCLE / 2f); i < POINT_IN_CIRCLE; i++) {
            focus_vec = focus_vec.times(mat);
            points[i] = focus1.plus(focus_vec);
        }
        for (int i = 0; i < Math.floor(POINT_IN_CIRCLE / 2f); i++) {
            focus_vec = focus_vec.times(mat);
            points[i] = focus2.plus(focus_vec);
        }
        return points;
    }

    @Override
    public int getPointsCount() {
        return POINT_IN_CIRCLE;
    }

}
