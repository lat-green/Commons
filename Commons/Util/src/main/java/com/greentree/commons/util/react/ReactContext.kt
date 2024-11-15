package com.greentree.commons.util.react

interface ReactContext {

	fun refresh()

	fun <T> useRef(initialValue: T, onClose: (T) -> Unit): Ref<T>
}

val REACT: ScopedValue<ReactContext> = ScopedValue.newInstance<ReactContext>()

fun <R> ReactContext.tryRun(block: () -> R): R {
	return if(REACT.isBound) {
		block()
	} else {
		var result: R? = null
		ScopedValue.where(REACT, this).run(Runnable {
			result = block()
		})
		result!!
	}
}

fun refresh() = REACT.get().refresh()