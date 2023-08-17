package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.IOResource

interface IOResourceLocation : ResourceLocation {

	override fun getResource(name: String): IOResource
	fun createNewResource(name: String): IOResource
	fun deleteResource(name: String): Boolean {
		val res = getResource(name)
		return res.delete()
	}

	fun createResource(name: String): IOResource {
		if(isExist(name)) deleteResource(name)
		return createResource(name)
	}
}