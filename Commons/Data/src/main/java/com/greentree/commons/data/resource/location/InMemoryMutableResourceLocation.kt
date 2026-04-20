package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.InMemoryProxyMutableResource
import com.greentree.commons.data.resource.InMemoryResource
import com.greentree.commons.data.resource.MutableResource

class InMemoryMutableResourceLocation(
	resources: Iterable<InMemoryResource>,
) : MutableResourceLocation {

	private val resources = resources.toMutableList()

	constructor(vararg resources: InMemoryResource) : this(listOf(*resources))

	override fun getResource(name: String): MutableResource =
		resources.firstOrNull { it.name == name } ?: InMemoryProxyMutableResource(name).also {
			resources.add(it)
		}
}
