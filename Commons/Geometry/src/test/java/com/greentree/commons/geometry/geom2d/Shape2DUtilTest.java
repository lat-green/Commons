package com.greentree.commons.geometry.geom2d;

import com.greentree.commons.geometry.geom2d.shape.Polygon2D;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Shape2DUtilTest {

    @Test
    void getMinimalConvexHull() {
        var sh = poligon(0, 0, 1, 0, .25f, .25f, 0, 1);
        assertFalse(Shape2DUtil.isClockwise(sh.getPoints()));
        var res = Shape2DUtil.getMinimalConvexHullGraham(sh.getPoints());
        assertFalse(Shape2DUtil.isClockwise(res));
        assertTrue(Shape2DUtil.isConvex(res));
        assertArrayEquals(res, Shape2DUtil.getVectors2f(0, 0, 1, 0, 0, 1));
    }

    private static Polygon2D poligon(float... fs) {
        return new Polygon2D(vecs(fs));
    }

    private static Vector2fc[] vecs(float... point) {
        if ((point.length & 1) == 1)
            throw new UnsupportedOperationException("the length of the array must be even");
        final var res = new Vector2fc[point.length / 2];
        for (int i = 0; i < point.length; i += 2)
            res[i / 2] = new Vector2f(point[i], point[i + 1]);
        return res;
    }

    @Test
    void triangulationTest() {
        final var arr = new Vector2f[]{new Vector2f(1, 0), new Vector2f(-1, 1), new Vector2f(2, 2),
                new Vector2f(0, 0),};
        final var res = Shape2DUtil.triangulation(arr);
        assertEquals(res.size(), 2);
    }

    @Test
    void isClockwiseFalse() {
        var sh = poligon(0, 0, 1, 0, 1, 1);
        assertFalse(Shape2DUtil.isClockwise(sh.getPoints()));
    }

    @Test
    void isClockwiseTrue() {
        var sh = poligon(0, 0, 1, 1, 1, 0);
        assertTrue(Shape2DUtil.isClockwise(sh.getPoints()));
    }

}
