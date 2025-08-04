package com.greentree.commons.react

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

interface ReactContextProvider : AutoCloseable {

	val requireRefresh: Boolean

	fun refresh()

	fun next(): ReactContext
}

@OptIn(ExperimentalContracts::class)
inline fun <R> ReactContextProvider.runReact(block: ReactContext.() -> R): R {
	contract {
		callsInPlace(block, InvocationKind.AT_LEAST_ONCE)
	}
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

