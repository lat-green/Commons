package com.greentree.commons.geometry.math;

import com.greentree.commons.math.MathLine3D;
import com.greentree.commons.math.MathPlane3D;
import com.greentree.commons.math.Mathf;
import org.joml.Vector3f;
import org.junit.jupiter.api.Test;

import static com.greentree.commons.geometry.TestUtil.assertLineEquals;
import static org.junit.jupiter.api.Assertions.*;

public class MathPlane3DTest {

    @Test
    void contact() {
        MathPlane3D p1 = new MathPlane3D(of(0, 0, 0), of(0, 0, 1));
        MathPlane3D p2 = new MathPlane3D(of(0, 0, 0), of(0, 1, 0));
        assertLineEquals(p1.contact(p2), new MathLine3D(of(0, 0, 0), of(-1, 0, 0)));
    }

    private Vector3f of(float x, float y, float z) {
        return new Vector3f(x, y, z);
    }

    @Test
    void distanse() {
        MathPlane3D p1 = new MathPlane3D(of(0, 0, 0), of(0, 0, 1));
        assertTrue(Mathf.equals(p1.distanse(of(0, 0, 0)), 0));
        assertTrue(Mathf.equals(p1.distanse(of(0, 1, 0)), 0));
        assertTrue(Mathf.equals(p1.distanse(of(0, 0, 1)), 1));
    }

    @Test
    void isCoplanar() {
        MathPlane3D p1 = new MathPlane3D(of(0, 0, 0), of(0, 0, 1));
        assertTrue(p1.isCoplanar(of(0, 0, 0)));
        assertTrue(p1.isCoplanar(of(0, 1, 0)));
        assertFalse(p1.isCoplanar(of(0, 0, 1)));
    }

    @Test
    void minPoint() {
        MathPlane3D p1 = new MathPlane3D(of(0, 0, 0), of(0, 0, 1));
        assertEquals(p1.minPoint(of(0, 0, 0)), of(0, 0, 0));
        assertEquals(p1.minPoint(of(0, 0, 1)), of(0, 0, 0));
        assertEquals(p1.minPoint(of(1, 1, 10)), of(1, 1, 0));
    }

}
