package com.greentree.commons.data.resource.location

data class PlusResourceLocation(
	val first: ResourceLocation,
	val second: ResourceLocation,
) : ResourceLocation {

	override fun getResourceOrNull(name: String) = first.getResourceOrNull(name) ?: second.getResourceOrNull(name)
}

operator fun ResourceLocation.plus(location: ResourceLocation) =
	PlusResourceLocation(this, location)