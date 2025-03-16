package com.greentree.commons.util.react

inline fun <R> ReactContext.useMemo(dependency: Any, block: () -> R): R {
	var valueRef by useRef<R>() as Ref<R>
	if(useDependency(dependency)) {
		valueRef = block()
	}
	return valueRef
}

inline fun <R> ReactContext.useMemoByHash(dependency: Any, block: () -> R): R {
	var valueRef by useRef<R>() as Ref<R>
	if(useDependencyByHash(dependency)) {
		valueRef = block()
	}
	return valueRef
}

inline fun <R : AutoCloseable> ReactContext.useMemoClose(dependency: Any = Unit, block: () -> R): R {
	var valueRef: R by useRef<R> {
		it?.close()
	} as Ref<R>
	if(useDependency(dependency)) {
		valueRef = block()
	}
	return valueRef
}

inline fun <R : AutoCloseable> ReactContext.useMemoCloseByHash(dependency: Any = Unit, block: () -> R): R {
	var valueRef: R by useRef<R> {
		it?.close()
	} as Ref<R>
	if(useDependencyByHash(dependency)) {
		valueRef = block()
	}
	return valueRef
}