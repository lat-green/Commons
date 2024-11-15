package com.greentree.commons.util.react

data object AutoTempReactContext : ReactContext {

	override fun refresh() {
	}

	override fun <T> useRef(initialValue: T, onClose: (T) -> Unit): Ref<T> {
		val ref = DataRef(initialValue, onClose)
		ref.close()
		return ref
	}
}