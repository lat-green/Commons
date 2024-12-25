package com.greentree.commons.util.react

interface ReactContextProvider : AutoCloseable {

	fun next(): ReactContext
}

inline fun <R> ReactContextProvider.runReact(block: ReactContext.() -> R): R {
	return next().block()
}

inline fun <R> ReactContextProvider.runDeepReact(block: ReactContext.() -> R): R {
	while(true) {
		val ctx = FlagReactContext(next())
		val result = ctx.block()
		if(!ctx.requireRefresh)
			return result
	}
}