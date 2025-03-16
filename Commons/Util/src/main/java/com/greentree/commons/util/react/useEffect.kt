package com.greentree.commons.util.react

inline fun ReactContext.useEffect(dependency: Any, block: () -> Unit) {
	val previous = usePrevious(dependency)
	if(previous != dependency) {
		block()
	}
}

inline fun ReactContext.useEffectByHash(dependency: Any, block: () -> Unit) {
	val hash = dependency.hashCode()
	val previous = usePrevious(hash)
	if(previous != hash) {
		block()
	}
}

@Deprecated("", ReplaceWith("block()"))
inline fun ReactContext.useEffect(block: () -> Unit) {
	block()
}

inline fun ReactContext.useEffectClose(dependency: Any, block: () -> AutoCloseable) {
	var closeablePrevious by useRef<AutoCloseable> {
		it?.close()
	}
	val previous = usePrevious(dependency)
	if(previous != dependency) {
		closeablePrevious = block()
	}
}

inline fun ReactContext.useEffectCloseByHash(dependency: Any, block: () -> AutoCloseable) {
	var closeablePrevious by useRef<AutoCloseable> {
		it?.close()
	}
	val hash = dependency.hashCode()
	val previous = usePrevious(hash)
	if(previous != hash) {
		closeablePrevious = block()
	}
}

inline fun ReactContext.useEffectClose(block: () -> AutoCloseable) {
	var previousCloseable by useRef<AutoCloseable> {
		it?.close()
	}
	previousCloseable = block()
}

