package com.greentree.commons.math.vector

interface AbstractMutableVector3<T> : AbstractMutableVector<T> {

    var x: T
    var y: T
    var z: T

    fun x(value: T) {
        this.x = value
    }

    fun y(value: T) {
        this.y = value
    }

    fun z(value: T) {
        this.z = value
    }

    override fun set(index: Int, value: T) {
        return when (index) {
            0 -> x = value
            1 -> y = value
            2 -> z = value
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun assign(other: AbstractVector<out T>) {
        if(other.size == 3) {
            x = other[0]
            y = other[1]
            z = other[2]
        } else
            throw IndexOutOfBoundsException()
    }

}
