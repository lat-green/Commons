package com.greentree.common.math.vector;

import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.AbstractVector2fKt;
import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.commons.math.vector.AbstractVector3fKt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector3fTest {

    @Test
    void getProjection() {
        var n = of(0, 1, 0);
        assertEquals(of(0, 0, 0).getProjection(n), of(0, 0));
        assertEquals(of(0, 1, 0).getProjection(n), of(0, 0));
        assertEquals(of(0, 1, 1).getProjection(n), of(0, 1.5f, 1).getProjection(n));
    }

    private AbstractVector3f of(float x, float y, float z) {
        return AbstractVector3fKt.vec3f(x, y, z);
    }

    private AbstractVector2f of(float x, float y) {
        return AbstractVector2fKt.vec2f(x, y);
    }

    @Test
    void testEquals1() {
        var v1 = AbstractVector3fKt.vec3f(1, 0, 0);
        var v2 = AbstractVector3fKt.vec3f(1, 0, 0);
        assertVec3fEquals(v1, v2);
    }

    private void assertVec3fEquals(AbstractVector3f a, AbstractVector3f b) {
        assertEquals(a.getX(), b.getX(), Mathf.EPS);
        assertEquals(a.getY(), b.getY(), Mathf.EPS);
        assertEquals(a.getZ(), b.getZ(), Mathf.EPS);
    }

    @Test
    void testEquals2() {
        var v1 = AbstractVector3fKt.vec3f(1, 1, -0);
        var v2 = AbstractVector3fKt.vec3f(1, 1, 0);
        assertVec3fEquals(v1, v2);
    }

    @Test
    void testGetProjection() {
        var v = AbstractVector3fKt.vec3f(1, 0, 0);
        var normal = AbstractVector3fKt.vec3f(0, 1, 0);
        assertEquals(v.getProjection(normal), v.plus(normal).getProjection(normal));

    }

}
