package com.greentree.commons.geometry;

import com.greentree.commons.math.MathLine3D;
import com.greentree.commons.math.Mathf;
import org.joml.Vector2fc;
import org.joml.Vector3fc;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtil {

    public static void assertVecEquals(Vector2fc a, Vector2fc b) {
        assertEquals(a.x(), b.x(), Mathf.EPS);
        assertEquals(a.y(), b.y(), Mathf.EPS);
    }

    public static void assertLineEquals(MathLine3D a, MathLine3D b) {
        assertVecEquals(a.p1(), b.p1());
        assertVecEquals(a.p2(), b.p2());
    }

    public static void assertVecEquals(Vector3fc a, Vector3fc b) {
        assertEquals(a.x(), b.x(), Mathf.EPS);
        assertEquals(a.y(), b.y(), Mathf.EPS);
        assertEquals(a.z(), b.z(), Mathf.EPS);
    }

}
