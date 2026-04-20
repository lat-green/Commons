package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.Resource

data class PlusResourceLocation(
	val first: ResourceLocation,
	val second: ResourceLocation,
) : ResourceLocation {

	override fun getResource(name: String): Resource {
		val f = first.getResource(name)
		if(f.exists())
			return f
		return second.getResource(name)
	}
}

operator fun ResourceLocation.plus(location: ResourceLocation) =
	PlusResourceLocation(this, location)