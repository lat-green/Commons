package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.IOResource
import com.greentree.commons.data.resource.Resource
import com.greentree.commons.data.resource.ResourceAction
import com.greentree.commons.data.resource.writeTo
import java.io.InputStream
import java.io.OutputStream
import java.net.MalformedURLException
import java.net.URL

class CacheResourceLocation(
	private val sourceLocation: ResourceLocation,
	private val cacheLocation: IOResourceLocation,
) : IOResourceLocation {

	override fun clear() {
		cacheLocation.clear()
	}

	@Synchronized
	override fun getResource(name: String): IOResource {
		return if(cacheLocation.isExist(name)) {
			if(sourceLocation.isExist(name)) {
				val cache = cacheLocation.getResource(name)
				val source = sourceLocation.getResource(name)
				cacheLocation.deleteResource(name)
				source.writeTo(cache, cache.lastModified())
				CacheResource(source, cache)
			} else {
				cacheLocation.deleteResource(name)
				IOResource.Null
			}
		} else if(sourceLocation.isExist(name)) {
			val cache = cacheLocation.createResource(name)
			val source = sourceLocation.getResource(name)
			source.writeTo(cache)
			CacheResource(source, cache)
		} else IOResource.Null
	}

	override fun createNewResource(name: String): IOResource {
		throw UnsupportedOperationException()
	}

	override fun deleteResource(name: String): Boolean {
		return cacheLocation.deleteResource(name)
	}

	override val lastModified
		get() = cacheLocation.lastModified

	class CacheResource(private val source: Resource, private val cache: IOResource) : IOResource {

		override val action: ResourceAction
			get() = source.action
		override val name: String
			get() = cache.name

		override fun length(): Long {
			return cache.length()
		}

		override fun lastModified(): Long {
			return cache.lastModified()
		}

		override fun open(): InputStream {
			return cache.open()
		}

		@Throws(MalformedURLException::class)
		override fun url(): URL {
			return cache.url()
		}

		override fun delete(): Boolean {
			return cache.delete()
		}

		override fun exists(): Boolean {
			return cache.exists()
		}

		override fun openWrite(): OutputStream {
			throw UnsupportedOperationException()
		}

		companion object {

			private const val serialVersionUID = 1L
		}
	}

	companion object {

		private const val serialVersionUID = 1L
	}
}