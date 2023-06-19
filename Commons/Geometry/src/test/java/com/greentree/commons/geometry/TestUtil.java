package com.greentree.commons.geometry;

import com.greentree.commons.math.MathLine3D;
import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.AbstractVector3f;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtil {

    public static final void assertVecEquals(AbstractVector2f a, AbstractVector2f b) {
        assertEquals(a.x(), b.x(), Mathf.EPS);
        assertEquals(a.y(), b.y(), Mathf.EPS);
    }

    public static final void assertLineEquals(MathLine3D a, MathLine3D b) {
        assertVecEquals(a.p1(), b.p1());
        assertVecEquals(a.p2(), b.p2());
    }

    public static final void assertVecEquals(AbstractVector3f a, AbstractVector3f b) {
        assertEquals(a.x(), b.x(), Mathf.EPS);
        assertEquals(a.y(), b.y(), Mathf.EPS);
        assertEquals(a.z(), b.z(), Mathf.EPS);
    }

}
