package com.greentree.commons.math.vector

import com.greentree.commons.action.observer.`object`.EventAction

class ActionVector2f : AbstractMutableVector2f {

	override var x: Float
		get() {
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
	private var actionX = false
	private var actionY = false
	private val action: EventAction<ActionVector2f> = EventAction<ActionVector2f>()
	private val X: ActionFloat
	private val Y: ActionFloat

	@JvmOverloads
	constructor(value: Float = 0f) : this(value, value)

	constructor(x: Float, y: Float) {
		X = ActionFloat(x)
		Y = ActionFloat(y)
		this.X.addListener { _ -> actionX = true }
		this.Y.addListener { _ -> actionY = true }
	}

	fun addListener(l: (ActionVector2f) -> Unit) {
		action.addListener(l)
	}

	fun tryAction() {
		if(actionX || actionY) {
			actionX = false
			actionY = false
			action.event(this)
		}
	}
}