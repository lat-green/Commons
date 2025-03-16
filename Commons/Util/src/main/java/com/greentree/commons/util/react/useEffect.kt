package com.greentree.commons.util.react

inline fun ReactContext.useEffect(dependency: Any, block: () -> Unit) {
	if(useDependency(dependency)) {
		block()
	}
}

inline fun ReactContext.useEffectByHash(dependency: Any, block: () -> Unit) {
	if(useDependencyByHash(dependency)) {
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
	if(useDependency(dependency)) {
		closeablePrevious = block()
	}
}

inline fun ReactContext.useEffectCloseByHash(dependency: Any, block: () -> AutoCloseable) {
	var closeablePrevious by useRef<AutoCloseable> {
		it?.close()
	}
	if(useDependencyByHash(dependency)) {
		closeablePrevious = block()
	}
}

inline fun ReactContext.useEffectClose(block: () -> AutoCloseable) {
	var previousCloseable by useRef<AutoCloseable> {
		it?.close()
	}
	previousCloseable = block()
}

