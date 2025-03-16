package com.greentree.commons.util.react

inline fun <R> ReactContext.useMemo(dependency: Any, block: () -> R): R {
	var valueRef by useRef<R>() as Ref<R>
	val code = dependency.hashCode()
	val previous = usePrevious(code)
	if(previous == null || previous != code) {
		valueRef = block()
	}
	return valueRef
}

inline fun <R : AutoCloseable> ReactContext.useMemoClose(dependency: Any = Unit, block: () -> R): R {
	var valueRef: R by useRef<R> {
		it?.close()
	} as Ref<R>
	val previous = usePrevious(dependency)
	if(previous == null || previous != dependency) {
		valueRef = block()
	}
	return valueRef
}