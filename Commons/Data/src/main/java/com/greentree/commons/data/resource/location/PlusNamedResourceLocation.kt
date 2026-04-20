package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.Resource

data class PlusNamedResourceLocation(
	val first: NamedResourceLocation,
	val second: NamedResourceLocation,
) : NamedResourceLocation {

	override val names: Iterable<String>
		get() = first.names + second.names

	override fun getResource(name: String): Resource {
		val f = first.getResource(name)
		if(f.exists())
			return f
		return second.getResource(name)
	}
}

operator fun NamedResourceLocation.plus(location: NamedResourceLocation) =
	PlusNamedResourceLocation(this, location)