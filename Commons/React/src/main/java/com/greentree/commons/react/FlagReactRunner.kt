package com.greentree.commons.react

data class FlagReactRunner<R>(
	val block: ReactContext.() -> R,
) : FlagReactContextProvider(), ReactRunner<R> {

	private var result = next().run(block)

	override fun runReact(): R {
		if(!requireRefresh)
			return result
		do {
			result = next().run(block)
		} while(requireRefresh)
		return result
	}
}