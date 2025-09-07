package com.greentree.commons.react

@Deprecated(
	"",
	ReplaceWith(
		"RequireReactRunner(FlagReactContextProvider().withRefresh(onRefresh), block)",
		imports = [
			"com.greentree.commons.react.RequireReactRunner",
			"com.greentree.commons.react.FlagReactContextProvider",
			"com.greentree.commons.react.withRefresh",
		]
	)
)
fun <R> EventReactRunner(
	block: ReactContext.() -> R,
	onRefresh: () -> Unit,
) = RequireReactRunner(FlagReactContextProvider().withRefresh(onRefresh), block)