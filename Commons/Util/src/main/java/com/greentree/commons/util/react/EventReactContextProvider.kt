package com.greentree.commons.util.react

data class EventReactContextProvider(
	val onRefresh: () -> Unit,
) : FlagReactContextProvider() {

	override fun refresh() {
		super.refresh()
		onRefresh()
	}
}