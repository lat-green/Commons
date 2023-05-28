package com.greentree.commons.math.vector

interface AbstractVector2<T> : AbstractVector<T> {

    val x: T
    val y: T

    override val size: Int
        get() = 2

    override fun component1(): T {
        return x
    }
    override fun component2(): T {
        return y
    }

    override fun get(index: Int): T {
        return when(index) {
            0 -> x
            1 -> y
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun iterator(): Iterator<T> {
        return listOf(x, y).iterator()
    }

}
