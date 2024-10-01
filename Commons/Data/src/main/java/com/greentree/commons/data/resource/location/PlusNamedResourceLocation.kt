package com.greentree.commons.data.resource.location

data class PlusNamedResourceLocation(
	val first: NamedResourceLocation,
	val second: NamedResourceLocation,
) : NamedResourceLocation {

	override val names: Iterable<String>
		get() = first.names + second.names

	override fun getResourceOrNull(name: String) = first.getResourceOrNull(name) ?: second.getResourceOrNull(name)
}

operator fun NamedResourceLocation.plus(location: NamedResourceLocation) =
	PlusNamedResourceLocation(this, location)