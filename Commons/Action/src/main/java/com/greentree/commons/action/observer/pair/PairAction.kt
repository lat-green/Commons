package com.greentree.commons.action.observer.pair

import com.greentree.commons.action.MultiAction

class PairAction<T1, T2> : MultiAction<(T1, T2) -> Unit>(), IPairAction<T1, T2> {

	override fun event(t1: T1, t2: T2) {
		for(l in listeners)
			l(t1, t2)
	}

	companion object {

		private const val serialVersionUID = 1L
	}
}
