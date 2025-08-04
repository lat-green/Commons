package com.greentree.commons.react

interface ReactRunner<R> {

	fun refresh()

	fun runReact(): R
}

fun <R> ReactContextProvider.runner(block: ReactContext.() -> R) = RequireReactRunner(this, block)