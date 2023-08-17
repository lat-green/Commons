package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.Resource
import java.io.Serializable

interface ResourceLocation : Serializable {

	fun clear() {
		throw UnsupportedOperationException()
	}

	fun add(location: ResourceLocation?): ResourceLocation? {
		return MultiResourceLocation.EMPTY.builder().add(location).add(this).build()
	}

	fun getResource(name: String): Resource

	fun isExist(name: String): Boolean {
		return getResource(name).exists()
	}
}