package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.Resource

interface NamedResourceLocation : ResourceLocation {

	val names: Iterable<String>

	fun toMap(): Map<String, Resource> {
		val map = HashMap<String, Resource>()
		for(name in names)
			map[name] = getResource(name)
		return map
	}
}