package com.greentree.commons.math.vector

import com.greentree.commons.math.Mathf
import java.io.Serializable


interface AbstractVector<T> : Serializable, Iterable<T> {

    operator fun component1(): T {
        return get(0)
    }
    operator fun component2(): T {
        return get(1)
    }
    operator fun component3(): T {
        return get(2)
    }
    operator fun component4(): T {
        return get(3)
    }

    fun normalize(length: Number = 1): AbstractVector<T>

    fun dot(other: AbstractVector<out T>): T

    fun distance(v: AbstractVector<out T>): Float {
        return Mathf.sqrt(distanceSqr(v))
    }

    fun distanceSqr(v: AbstractVector<out T>): Float {
        checkSize(this, v)
        return (this - v).lengthSquared()
    }

    operator fun get(index: Int): T

    operator fun div(other: T): AbstractVector<T>
    operator fun times(other: T): AbstractVector<T>

    operator fun plus(other: T): AbstractVector<T>
    operator fun minus(other: T): AbstractVector<T>

    operator fun div(other: AbstractVector<out T>): AbstractVector<T>
    operator fun times(other: AbstractVector<out T>): AbstractVector<T>

    operator fun plus(other: AbstractVector<out T>): AbstractVector<T>
    operator fun minus(other: AbstractVector<out T>): AbstractVector<T>

    operator fun unaryPlus(): AbstractVector<T>
    operator fun unaryMinus(): AbstractVector<T>


    fun length(): Float {
        return Mathf.sqrt(lengthSquared())
    }

    fun lengthSquared(): Float

    /*
	 * @return count of components in this vector
	 */
    public val size: Int

    companion object {

        inline fun <T> checkSize(a: AbstractVector<out T>, b: AbstractVector<out T>) {
            require(a.size == b.size) { "the size of the vectors are different $a $b" }
        }
    }

    override fun iterator(): Iterator<T> {
        return AbstractVectorIterator(this)
    }

    class AbstractVectorIterator<T>(private val vector: AbstractVector<out T>) : Iterator<T> {

        private var index = 0

        override fun hasNext(): Boolean {
            return index < vector.size
        }

        override fun next(): T {
            return vector[index++]
        }
    }

}
inline fun <T> AbstractVector<T>.checkSize(other: AbstractVector<out T>) {
    AbstractVector.checkSize(this, other)
}