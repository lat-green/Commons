package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.Resource

data class MapResourceLocation<R : Resource>(
	val resources: Iterable<R>,
) : ResourceLocation {

	constructor(vararg resources: R) : this(listOf(*resources))

	override fun getResourceOrNull(name: String) = resources.firstOrNull { it.name == name }
}