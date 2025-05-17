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

data class WithRefreshReactContextProvider(
	val origin: ReactContextProvider,
	val onRefresh: () -> Unit,
) : ReactContextProvider by origin {

	override fun next() = origin.next().withRefresh(onRefresh)

	override fun refresh() {
		onRefresh()
		origin.refresh()
	}
}

fun ReactContextProvider.withRefresh(onRefresh: () -> Unit) = WithRefreshReactContextProvider(this, onRefresh)