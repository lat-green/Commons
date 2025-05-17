package com.greentree.commons.util.react

inline fun <R> ReactContext.useMemo(dependency: Any, block: () -> R): R {
	var valueRef by useRef<R>() as Ref<R>
	if(useDependency(dependency)) {
		valueRef = block()
	}
	return valueRef
}

inline fun <R> ReactContext.useMemo(block: () -> R): R {
	var valueRef by useRef<R>() as Ref<R>
	if(useFirst()) {
		valueRef = block()
	}
	return valueRef
}

@Deprecated("not use Unit as dependency", ReplaceWith("useMemo(block)"))
inline fun <R> ReactContext.useMemo(dependency: Unit, block: () -> R) = useMemo(block)

inline fun <R> ReactContext.useMemoByHash(dependency: Any, block: () -> R): R {
	var valueRef by useRef<R>() as Ref<R>
	if(useDependencyByHash(dependency)) {
		valueRef = block()
	}
	return valueRef
}

@Deprecated("not use Unit as dependency", ReplaceWith("useMemo(block)"))
inline fun <R> ReactContext.useMemoByHash(dependency: Unit, block: () -> R) = useMemo(block)

inline fun <R : AutoCloseable> ReactContext.useMemoClose(dependency: Any, block: () -> R): R {
	var valueRef: R by useRefClose<R> {
		it.close()
	} as Ref<R>
	if(useDependency(dependency)) {
		valueRef = block()
	}
	return valueRef
}

inline fun <R : AutoCloseable> ReactContext.useMemoClose(block: () -> R): R {
	var valueRef: R by useRefClose<R> {
		it.close()
	} as Ref<R>
	if(useFirst()) {
		valueRef = block()
	}
	return valueRef
}

@Deprecated("not use Unit as dependency", ReplaceWith("useMemoClose(block)"))
inline fun <R : AutoCloseable> ReactContext.useMemoClose(dependency: Unit, block: () -> R) = useMemoClose(block)

inline fun <R : AutoCloseable> ReactContext.useMemoCloseByHash(dependency: Any, block: () -> R): R {
	var valueRef: R by useRefClose<R> {
		it.close()
	} as Ref<R>
	if(useDependencyByHash(dependency)) {
		valueRef = block()
	}
	return valueRef
}

@Deprecated("not use Unit as dependency", ReplaceWith("useMemoClose(block)"))
inline fun <R : AutoCloseable> ReactContext.useMemoCloseByHash(dependency: Unit, block: () -> R) = useMemoClose(block)