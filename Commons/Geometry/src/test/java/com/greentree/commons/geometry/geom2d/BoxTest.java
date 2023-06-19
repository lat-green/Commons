package com.greentree.commons.geometry.geom2d;

import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.Vector2f;
import com.greentree.commons.util.cortege.Pair;
import org.joml.Matrix3f;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static com.greentree.commons.geometry.TestUtil.assertVecEquals;

public class BoxTest {

    private static final Rectangle SQUARE = new Rectangle(vec(0, 0), 2, 2);

    static Stream<Pair<AbstractVector2f, AbstractVector2f>> min_point() {
        final var s = new ArrayList<Pair<AbstractVector2f, AbstractVector2f>>();
        s.add(new Pair<>(vec(0, 1), vec(0, 1)));
        s.add(new Pair<>(vec(0, 2), vec(0, 1)));
        s.add(new Pair<>(vec(2, .75f), vec(1, .75f)));
        final var res = new ArrayList<Pair<AbstractVector2f, AbstractVector2f>>();
        for (var p : s) {
            res.add(new Pair<>(vec(p.first.x(), p.first.y()), vec(p.seconde.x(), p.seconde.y())));
            res.add(new Pair<>(vec(-p.first.x(), p.first.y()), vec(-p.seconde.x(), p.seconde.y())));
            res.add(new Pair<>(vec(p.first.x(), -p.first.y()), vec(p.seconde.x(), -p.seconde.y())));
            res.add(new Pair<>(vec(-p.first.x(), -p.first.y()),
                    vec(-p.seconde.x(), -p.seconde.y())));
            res.add(new Pair<>(vec(p.first.y(), p.first.x()), vec(p.seconde.y(), p.seconde.x())));
            res.add(new Pair<>(vec(-p.first.y(), p.first.x()), vec(-p.seconde.y(), p.seconde.x())));
            res.add(new Pair<>(vec(p.first.y(), -p.first.x()), vec(p.seconde.y(), -p.seconde.x())));
            res.add(new Pair<>(vec(-p.first.y(), -p.first.x()),
                    vec(-p.seconde.y(), -p.seconde.x())));
        }
        return res.stream();
    }

    private static Vector2f vec(double x, double y) {
        return new Vector2f((float) x, (float) y);
    }

    static Stream<Pair<Pair<Matrix3f, AbstractVector2f>, AbstractVector2f>> model_min_point() {
        final var res = new ArrayList<Pair<Pair<Matrix3f, AbstractVector2f>, AbstractVector2f>>();
        res.add(new Pair<>(new Pair<>(new Matrix3f(10, 0, 0, 0, 1, 0, 0, 0, 1), vec(0, 10)),
                vec(0, 1)));
        return res.stream();
    }

    @MethodSource
    @ParameterizedTest
    void min_point(Pair<AbstractVector2f, AbstractVector2f> p) {
        assertVecEquals(SQUARE.nearestPoint(p.first), p.seconde);
    }

}
