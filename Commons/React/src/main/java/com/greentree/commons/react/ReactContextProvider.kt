package com.greentree.commons.react

interface ReactContextProvider : AutoCloseable {

	val requireRefresh: Boolean

	fun refresh()

	fun next(): ReactContext
}

inline fun <R> ReactContextProvider.runReact(block: ReactContext.() -> R): R {
	var result: R
	do {
		result = next().run(block)
	} while(requireRefresh)
	return result
}

inline fun <R> ReactContextProvider.runReactIfRequire(block: ReactContext.() -> R): R? {
	var result: R
	if(!requireRefresh)
		return null
	do {
		result = next().run(block)
	} while(requireRefresh)
	return result
}

