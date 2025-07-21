package com.greentree.commons.react

@Deprecated("", ReplaceWith("FlagReactContextProvider().withRefresh(onRefresh)", "com.greentree.commons.util.react.*"))
data class EventReactContextProvider(
	val onRefresh: () -> Unit,
) : FlagReactContextProvider() {

	override fun refresh() {
		super.refresh()
		onRefresh()
	}
}