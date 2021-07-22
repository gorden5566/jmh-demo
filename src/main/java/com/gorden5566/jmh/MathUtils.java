package com.gorden5566.jmh;

/**
 * @author gorden5566
 * @date 2021/07/23
 */
public class MathUtils {
    private static final int MAXIMUM_CAPACITY = 1 << 30;

    public static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    public static final int newCapacity(int cap) {
        int n = 1;
        while (n < cap) {
            n = n << 1;
        }
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n;
    }
}
