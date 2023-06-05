package com.greentree.commons.math.vector.ord

import com.greentree.commons.math.vector.AbstractFloatVector

class Link<V : AbstractFloatVector?>(val origine: V, val index: Int) : FloatVectorOrd {

    override fun get(): Float {
        return origine!![index]
    }
}