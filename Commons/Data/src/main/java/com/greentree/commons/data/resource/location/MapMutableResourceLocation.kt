package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.MutableResource

data class MapMutableResourceLocation<R : MutableResource>(
	val resources: Iterable<R>,
) : MutableResourceLocation {

	constructor(vararg resources: R) : this(listOf(*resources))

	override fun getResourceOrNull(name: String) = resources.firstOrNull { it.name == name }
}