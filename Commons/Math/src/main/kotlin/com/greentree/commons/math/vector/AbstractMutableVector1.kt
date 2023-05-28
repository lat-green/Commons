package com.greentree.commons.math.vector

interface AbstractMutableVector1<T> : AbstractMutableVector<T> {

    var x: T

    override fun set(index: Int, value: T) {
        return when(index) {
            0 -> x = value
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun assign(other: AbstractVector<out T>) {
        if(other.size == 1) {
            x = other[0]
        } else
            throw IndexOutOfBoundsException()
    }

}
