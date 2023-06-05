package com.greentree.commons.math.vector.ord

class Value : ChangeableFloatVectorOrd {
    private var value = 0f

    constructor()
    constructor(value: Float) {
        this.value = value
    }

    override fun get(): Float {
        return value
    }

    override fun set(x: Float) {
        value = x
    }
}