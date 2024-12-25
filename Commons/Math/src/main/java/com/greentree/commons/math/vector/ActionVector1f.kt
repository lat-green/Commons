package com.greentree.commons.math.vector

import com.greentree.commons.action.observer.`object`.EventAction
import java.util.function.Consumer

class ActionVector1f : AbstractVector1f {

	override var x: Float
		get() {
			tryAction()
			return X.get()
		}
		set(value) = X.set(value)
	private var actionX = false
	private val action: EventAction<ActionVector1f> = EventAction<ActionVector1f>()
	private val X: ActionFloat

	constructor(x: Float) {
		X = ActionFloat(x)
		this.X.addListener { _ -> actionX = true }
	}

	fun addListener(l: (ActionVector1f) -> Unit) {
		action.addListener(l)
	}

	fun tryAction() {
		if(actionX) {
			actionX = false
			action.event(this)
		}
	}
}