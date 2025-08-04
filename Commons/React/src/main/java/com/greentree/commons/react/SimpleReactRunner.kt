package com.greentree.commons.react

data class SimpleReactRunner<R>(
	val provider: ReactContextProvider = FlagReactContextProvider(),
	val block: ReactContext.() -> R,
) : ReactRunner<R> {

	override fun refresh() {
		provider.refresh()
	}

	override fun runReact(): R {
		var result: R
		do {
			result = provider.next().run(block)
		} while(provider.requireRefresh)
		return result
	}
}