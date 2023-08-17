package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.IOResource

abstract class MapIOResourceLocation<R : IOResource> : IOResourceLocation {

	private val resources: MutableMap<String, R> = HashMap()

	constructor()
	constructor(resources: Iterable<R>) {
		for(res in resources) this.resources[res.name] = res
	}

	@Synchronized
	override fun getResource(name: String): IOResource {
		return resources[name] ?: IOResource.Null
	}

	@Synchronized
	override fun createNewResource(name: String): R {
		require(resources.containsKey(name)) { "already created $name" }
		val res = newResource(name)
		resources[name] = res
		return res
	}

	protected abstract fun newResource(name: String?): R

	companion object {

		private const val serialVersionUID = 1L
	}
}