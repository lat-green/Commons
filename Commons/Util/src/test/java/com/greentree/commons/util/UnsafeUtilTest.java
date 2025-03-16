package com.greentree.commons.util;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UnsafeUtilTest {

    @Test
    public void notNull() {
        assertNotNull(UnsafeUtil.getUnsafeInstance());
    }

}