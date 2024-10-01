package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.FileResource
import com.greentree.commons.data.resource.Resource
import com.greentree.commons.data.resource.ResourceNotFound
import com.greentree.commons.data.resource.URLFileResource
import java.net.URL

class ClassLoaderResourceLocation(private val loader: ClassLoader) : ResourceLocation {

	constructor(cls: Class<*> = ClassLoaderResourceLocation::class.java) : this(cls.classLoader)

	override fun getResourceOrNull(name: String): Resource? {
		TODO("Not yet implemented")
	}

	override fun getResource(name: String): FileResource {
		val url = getURL(name) ?: throw ResourceNotFound(name)
		return URLFileResource(url)
	}

	private fun getURL(name: String): URL? {
		return loader.getResource(name)
	}

	override fun toString(): String {
		return "ClassLoaderResourceLocation [$loader]"
	}
}