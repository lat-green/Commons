package com.greentree.commons.math.vector

import com.greentree.commons.math.Mathf

class NormalVector2f(private val angle: Float): AbstractVector2f {

    override val x: Float
        get() = Mathf.cos(angle)
    override val y: Float
        get() = Mathf.sin(angle)

    constructor(other: AbstractVector2f) : this(angle(other))

    override fun lengthSquared(): Float {
        return 1f
    }

    override fun length(): Float {
        return 1f
    }

    private companion object {
        fun angle(vector: AbstractVector2f): Float {
            val n = vector.normalize()
            val angle = Mathf.acos(n.x)
            if(n.y < 0)
                return -angle
            return angle
        }
    }

}