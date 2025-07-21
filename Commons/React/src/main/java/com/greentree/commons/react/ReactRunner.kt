package com.greentree.commons.react

fun interface ReactRunner<R> {

	fun runReact(): R
}

fun <R> ReactContextProvider.runner(block: ReactContext.() -> R): ReactRunner<R> {
	val result by lazy { runReact(block) }
	return ReactRunner {
		runReactIfRequire(block) ?: result
	}
}