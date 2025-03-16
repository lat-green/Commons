package com.greentree.commons.util.react

fun interface ReactRunner<R> {

	fun runReact(): R
}

fun <R> ReactContextProvider.runner(block: ReactContext.() -> R): ReactRunner<R> {
	val result = runReact(block)
	return ReactRunner {
		runReactIfRequire(block) ?: result
	}
}