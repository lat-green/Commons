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

inline fun <R> useMemo(dependency: Any, block: () -> R) = REACT.get().useMemo(dependency, block)
