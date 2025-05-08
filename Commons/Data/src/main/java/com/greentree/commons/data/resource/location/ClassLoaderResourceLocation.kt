package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.data.resource.URLFileResource
import java.net.URL

class ClassLoaderResourceLocation(private val loader: ClassLoader) : ResourceLocation {

	constructor(cls: Class<*> = ClassLoaderResourceLocation::class.java) : this(cls.classLoader)

	override fun getResourceOrNull(name: String): Resource? {
		val url = getURL(name) ?: return null
		return URLFileResource(url)
	}

	private fun getURL(name: String): URL? {
		return loader.getResource(name)
	}

	override fun toString(): String {
		return "ClassLoaderResourceLocation [$loader]"
	}
}