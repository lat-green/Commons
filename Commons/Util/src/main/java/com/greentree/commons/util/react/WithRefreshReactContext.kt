package com.greentree.commons.util.react

data class WithRefreshReactContext(
	val origin: ReactContext,
	val onRefresh: () -> Unit,
) : ReactContext by origin {

	override fun refresh() {
		onRefresh()
		origin.refresh()
	}
}

fun ReactContext.withRefresh(onRefresh: () -> Unit) = WithRefreshReactContext(this, onRefresh)