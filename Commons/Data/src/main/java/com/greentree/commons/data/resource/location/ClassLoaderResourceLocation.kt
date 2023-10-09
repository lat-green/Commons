package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.data.resource.URLResource
import java.net.URL
import java.util.*

class ClassLoaderResourceLocation @JvmOverloads constructor(private val cls: Class<*> = ClassLoaderResourceLocation::class.java) :
	ResourceLocation {

	override fun equals(obj: Any?): Boolean {
		if(this === obj) return true
		if(obj == null || javaClass != obj.javaClass) return false
		val other = obj as ClassLoaderResourceLocation
		return cls == other.cls
	}

	val loader: ClassLoader
		get() = cls.classLoader

	override fun getResource(name: String): Resource {
		val url = getURL(name) ?: return Resource.Null
		return URLResource(url)
	}

	private fun getURL(name: String): URL? {
		return loader.getResource(name)
	}

	override fun hashCode(): Int {
		return Objects.hash(cls)
	}

	override fun toString(): String {
		return "ClassLoaderResourceLocation [$cls]"
	}

	companion object {

		private const val serialVersionUID = 1L
	}
}