package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.data.resource.URLResource
import java.net.URL

class ClassLoaderResourceLocation(private val loader: ClassLoader) :
	ResourceLocation {

	constructor(cls: Class<*> = ClassLoaderResourceLocation::class.java) : this(cls.classLoader)

	override val lastModified: Long
		get() = URLResource(getURL("")!!).lastModified()

	override fun getResource(name: String): Resource {
		val url = getURL(name) ?: return Resource.Null
		return URLResource(url)
	}

	private fun getURL(name: String): URL? {
		return loader.getResource(name)
	}

	override fun toString(): String {
		return "ClassLoaderResourceLocation [$loader]"
	}
}