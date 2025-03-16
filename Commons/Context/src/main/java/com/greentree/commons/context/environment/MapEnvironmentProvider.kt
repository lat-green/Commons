package com.greentree.commons.context.environment

data class MapEnvironmentProvider(
	override val name: String,
	val origin: Map<String, String>,
) : EnvironmentProvider, Map<String, String> by origin {

	constructor(
		name: String,
		vararg origin: Pair<String, String>,
	) : this(name, mapOf(*origin))
}