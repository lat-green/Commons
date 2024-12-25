package com.greentree.commons.action.observer.run

import com.greentree.commons.action.MultiAction

open class RunAction : MultiAction<Runnable>(), IRunnableAction {

	override fun event() {
		for(l in listeners)
			l.run()
	}

	companion object {

		private const val serialVersionUID = 1L
	}
}
