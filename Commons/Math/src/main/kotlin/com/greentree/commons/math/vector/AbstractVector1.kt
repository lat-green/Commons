package com.greentree.commons.math.vector

interface AbstractVector1<T> : AbstractVector<T> {

    val x: T

    override val size: Int
        get() = 1

    override fun component1(): T {
        return x
    }

    override fun get(index: Int): T {
        return when(index) {
            0 -> x
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun iterator(): Iterator<T> {
        return listOf(x).iterator()
    }

}
