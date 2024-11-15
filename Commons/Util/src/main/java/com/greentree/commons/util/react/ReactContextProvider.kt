package com.greentree.commons.util.react

interface ReactContextProvider : AutoCloseable {

	val requireRefresh: Boolean
	fun next(): ReactContext
}

inline fun <R> ReactContextProvider.runReact(block: ReactContext.() -> R): R {
	return next().block()
}