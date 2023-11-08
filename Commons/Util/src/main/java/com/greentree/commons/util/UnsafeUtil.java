package com.greentree.commons.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeUtil {

    private static Unsafe instance;

    public static Unsafe getUnsafeInstance() {
        if (instance == null)
            instance = getUnsafeInstance0();
        return instance;
    }

    private static Unsafe getUnsafeInstance0() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get Unsafe instance");
        }
    }

}
