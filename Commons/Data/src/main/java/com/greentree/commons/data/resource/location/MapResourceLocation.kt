package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.NotFoundResource
import com.greentree.commons.data.resource.Resource

data class MapResourceLocation<R : Resource>(
	val resources: Iterable<R>,
) : ResourceLocation {

	constructor(vararg resources: R) : this(listOf(*resources))

	override fun getResource(name: String): Resource =
		resources.firstOrNull { it.name == name } ?: NotFoundResource(name)
}