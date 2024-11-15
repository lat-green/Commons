package com.greentree.commons.util.react

inline fun <R> ReactContext.useMemo(dependency: Any, block: () -> R): R {
	var valueRef by useRef<R>() as Ref<R>
	val previous = usePrevious(dependency)
	if(previous == null || previous != dependency) {
		valueRef = block()
	}
	return valueRef
}

inline fun <R> useMemo(dependency: Any, block: () -> R) = REACT.get().useMemo(dependency, block)
