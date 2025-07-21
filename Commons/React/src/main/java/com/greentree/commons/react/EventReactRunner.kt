package com.greentree.commons.react

data class EventReactRunner<R>(
	val block: ReactContext.() -> R,
	val onRefresh: () -> Unit,
) : FlagReactContextProvider(), ReactRunner<R> {

	private var result = next().run(block)

	override fun runReact(): R {
		if(requireRefresh)
			do {
				result = next().run(block)
			} while(requireRefresh)
		return result
	}

	override fun refresh() {
		super.refresh()
		onRefresh()
	}
}