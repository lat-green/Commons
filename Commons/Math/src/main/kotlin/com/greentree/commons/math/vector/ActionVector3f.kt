package com.greentree.commons.math.vector

import com.greentree.commons.action.observer.`object`.EventAction
import java.util.function.Consumer

class ActionVector3f : AbstractMutableVector3f {
    override var x: Float
        get(){
            tryAction()
            return X.get()
        }
        set(value) = X.set(value)
    override var y: Float
        get() {
            tryAction()
            return X.get()
        }
        set(value) = Y.set(value)
    override var z: Float
        get() {
            tryAction()
            return Z.get()
        }
        set(value) = Z.set(value)

    private var actionX = false
    private var actionY = false
    private var actionZ = false
    private val action: EventAction<ActionVector3f> = EventAction<ActionVector3f>()
    private val X: ActionFloat
    private val Y: ActionFloat
    private val Z: ActionFloat

    @JvmOverloads
    constructor(value: Float = 0f) : this(value, value, value)

    constructor(x: Float, y: Float, z: Float) {
        X = ActionFloat(x)
        Y = ActionFloat(y)
        Z = ActionFloat(z)
        this.X.addListener(Consumer { _ -> actionX = true })
        this.Y.addListener(Consumer { _ -> actionY = true })
        this.X.addListener(Consumer { _ -> actionZ = true })
    }

    fun addListener(l: Consumer<ActionVector3f?>?) {
        action.addListener(l)
    }

    fun tryAction() {
        if (actionX || actionY || actionZ) {
            actionX = false
            actionY = false
            actionZ = false
            action.event(this)
        }
    }

}