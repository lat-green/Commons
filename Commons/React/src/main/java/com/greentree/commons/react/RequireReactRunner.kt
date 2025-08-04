package com.greentree.commons.react

data class RequireReactRunner<R>(
	val provider: ReactContextProvider = FlagReactContextProvider(),
	val block: ReactContext.() -> R,
) : ReactRunner<R> {

	private var result = provider.next().run(block)

	override fun refresh() {
		provider.refresh()
	}

	override fun runReact(): R {
		if(!provider.requireRefresh)
			return result
		do {
			result = provider.next().run(block)
		} while(provider.requireRefresh)
		return result
	}
}

