package com.greentree.commons.util.react

class FlagReactContext(
	val origin: ReactContext,
) : ReactContext {

	var requireRefresh: Boolean = false
		private set
		get() {
			val result = field
			field = false
			return result
		}

	override fun refresh() {
		requireRefresh = true
		origin.refresh()
	}

	override fun <T> useRef(initialValue: T, onClose: (T) -> Unit) = origin.useRef(initialValue, onClose)
}