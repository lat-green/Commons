package com.greentree.common.math.vector;

import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.FinalVector2f;
import com.greentree.commons.math.vector.Vector2f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatrixTest {

    @Test
    void Matrix2frot2D() {
        var r = Mathf.rot2(Mathf.PI * 0.5f);
        AbstractVector2f vec = new FinalVector2f(1, 0);
        vec = vec.times(r);
        assertVec2fEquals(vec, new Vector2f(0, 1));
        vec = vec.times(r);
        assertVec2fEquals(vec, new Vector2f(-1, 0));
        vec = vec.times(r);
        assertVec2fEquals(vec, new Vector2f(0, -1));
        vec = vec.times(r);
        assertVec2fEquals(vec, new Vector2f(1, 0));
    }

    private void assertVec2fEquals(AbstractVector2f a, AbstractVector2f b) {
        assertEquals(a.getX(), b.getX(), Mathf.EPS);
        assertEquals(a.getY(), b.getY(), Mathf.EPS);
    }

}
