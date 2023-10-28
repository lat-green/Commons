package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.Resource
import java.io.Serializable

interface ResourceLocation : Serializable {

	val lastModified: Long

	fun clear() {
		throw UnsupportedOperationException()
	}

	fun getResource(name: String): Resource

	fun isExist(name: String): Boolean {
		return getResource(name).exists()
	}
}

operator fun ResourceLocation.get(name: String) = getResource(name)
operator fun ResourceLocation.plus(location: ResourceLocation): ResourceLocation =
	MultiResourceLocation.EMPTY.builder().add(location).add(this).build()