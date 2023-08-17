package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.InMemoryResource

class InMemoryResourceLocation : MapIOResourceLocation<InMemoryResource>, IOResourceLocation {
	constructor()
	constructor(resources: Iterable<InMemoryResource>) : super(resources)

	override fun newResource(name: String?): InMemoryResource {
		return InMemoryResource(name)
	}

	companion object {

		private const val serialVersionUID = 1L
	}
}