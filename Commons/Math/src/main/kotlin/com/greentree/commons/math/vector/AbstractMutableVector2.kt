package com.greentree.commons.math.vector

interface AbstractMutableVector2<T> : AbstractMutableVector<T>, AbstractVector2<T> {

    override var x: T
    override var y: T

    override fun set(index: Int, value: T) {
        return when(index) {
            0 -> x = value
            1 -> y = value
            else -> throw IndexOutOfBoundsException()
        }
    }

    fun assign(other: AbstractVector2<out T>) {
        x = other.x
        y = other.y
    }

    override fun assign(other: AbstractVector<out T>) {
        if(other.size == 2) {
            x = other[0]
            y = other[1]
        } else
            throw IndexOutOfBoundsException()
    }

}
