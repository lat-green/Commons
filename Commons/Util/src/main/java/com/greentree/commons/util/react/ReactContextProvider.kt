package com.greentree.commons.util.react

interface ReactContextProvider : AutoCloseable {

	fun next(): ReactContext
}

inline fun <R> ReactContextProvider.runReact(block: ReactContext.() -> R): R {
	return next().block()
}