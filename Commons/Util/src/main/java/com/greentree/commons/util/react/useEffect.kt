package com.greentree.commons.util.react

inline fun ReactContext.useEffect(dependency: Any, block: () -> Unit) {
	val previous = usePrevious(dependency)
	if(previous == null || previous != dependency) {
		block()
	}
}

inline fun useEffect(dependency: Any, block: () -> Unit) = REACT.get().useEffect(dependency, block)

@Deprecated("", ReplaceWith("block()"))
inline fun ReactContext.useEffect(block: () -> Unit) {
	block()
}

@Deprecated("", ReplaceWith("block()"))
inline fun useEffect(block: () -> Unit) {
	REACT.get()
	block()
}

inline fun ReactContext.useEffectClose(dependency: Any, block: () -> AutoCloseable) {
	var closeablePrevious by useRef<AutoCloseable> {
		it?.close()
	}
	val dependencyPrevious = usePrevious(dependency)
	if(dependencyPrevious != dependency) {
		closeablePrevious?.close()
		closeablePrevious = block()
	}
}

inline fun useEffectClose(dependency: Any, block: () -> AutoCloseable) = REACT.get().useEffectClose(dependency, block)

inline fun ReactContext.useEffectClose(block: () -> AutoCloseable) {
	var previousCloseable by useRef<AutoCloseable> {
		it?.close()
	}
	previousCloseable?.close()
	previousCloseable = block()
}

inline fun useEffectClose(block: () -> AutoCloseable) = REACT.get().useEffectClose(block)

