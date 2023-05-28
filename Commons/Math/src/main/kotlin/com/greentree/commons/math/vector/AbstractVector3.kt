package com.greentree.commons.math.vector

interface AbstractVector3<T> : AbstractVector<T> {

    val x: T
    val y: T
    val z: T

    override val size: Int
        get() = 3

    override fun component1(): T {
        return x
    }
    override fun component2(): T {
        return y
    }
    override fun component3(): T {
        return z
    }

    override fun get(index: Int): T {
        return when(index) {
            0 -> x
            1 -> y
            2 -> z
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun iterator(): Iterator<T> {
        return listOf(x, y, z).iterator()
    }

}
