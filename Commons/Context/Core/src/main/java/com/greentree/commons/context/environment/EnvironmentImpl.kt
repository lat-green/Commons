package com.greentree.commons.context.environment

data class EnvironmentImpl(
	val providers: Sequence<EnvironmentProvider>,
) : Environment, AbstractMap<String, String>() {

	override val entries
		get() = providers.flatMap { it.entries }.toSet()
}