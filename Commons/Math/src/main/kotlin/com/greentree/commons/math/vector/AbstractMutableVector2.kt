package com.greentree.commons.math.vector

interface AbstractMutableVector2<T> : AbstractMutableVector<T>, AbstractVector2<T> {

    override var x: T
    override var y: T


    fun set(x: T, y: T): AbstractMutableVector2<T> {
        this.x = x
        this.y = y
        return this
    }

    fun set(xy: AbstractVector2<T>): AbstractMutableVector2<T> {
        this.x = xy.x
        this.y = xy.y
        return this
    }

    fun x(value: T): AbstractMutableVector2<T> {
        this.x = value
        return this
    }

    fun y(value: T): AbstractMutableVector2<T> {
        this.y = value
        return this
    }

    override fun set(index: Int, value: T) {
        return when (index) {
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
