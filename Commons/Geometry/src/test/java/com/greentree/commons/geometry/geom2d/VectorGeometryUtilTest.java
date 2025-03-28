package com.greentree.commons.geometry.geom2d;

import com.greentree.commons.geometry.geom2d.shape.Polygon2D;
import com.greentree.commons.geometry.geom2d.util.VectorGeometryUtil;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.FinalVector2f;
import com.greentree.commons.math.vector.Vector2f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VectorGeometryUtilTest {

    @Test
    void getMinimalConvexHull() {
        var sh = poligon(0, 0, 1, 0, .25f, .25f, 0, 1);
        assertFalse(VectorGeometryUtil.isClockwise(sh.getPoints()));
        var res = VectorGeometryUtil.getMinimalConvexHullGraham(sh.getPoints());
        assertFalse(VectorGeometryUtil.isClockwise(res));
        assertTrue(VectorGeometryUtil.isConvex(res));
        assertArrayEquals(res, VectorGeometryUtil.getVectors2f(0, 0, 1, 0, 0, 1));
    }

    private static Polygon2D poligon(float... fs) {
        return new Polygon2D(vecs(fs));
    }

    private static AbstractVector2f[] vecs(float... point) {
        if ((point.length & 1) == 1)
            throw new UnsupportedOperationException("the length of the array must be even");
        final var res = new AbstractVector2f[point.length / 2];
        for (int i = 0; i < point.length; i += 2)
            res[i / 2] = new FinalVector2f(point[i], point[i + 1]);
        return res;
    }

    @Test
    void triangulationTest() {
        final var arr = new Vector2f[]{new Vector2f(1, 0), new Vector2f(-1, 1), new Vector2f(2, 2),
                new Vector2f(0, 0),};
        final var res = VectorGeometryUtil.triangulation(arr);
        assertEquals(res.size(), 2);
    }

    @Test
    void isClockwiseFalse() {
        var sh = poligon(0, 0, 1, 0, 1, 1);
        assertFalse(VectorGeometryUtil.isClockwise(sh.getPoints()));
    }

    @Test
    void isClockwiseTrue() {
        var sh = poligon(0, 0, 1, 1, 1, 0);
        assertTrue(VectorGeometryUtil.isClockwise(sh.getPoints()));
    }

}
