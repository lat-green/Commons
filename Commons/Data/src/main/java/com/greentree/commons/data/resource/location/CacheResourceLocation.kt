package com.greentree.commons.data.resource.location

import com.greentree.commons.data.resource.FileResource
import com.greentree.commons.data.resource.MutableFileResource
import com.greentree.commons.data.resource.ParentResource
import com.greentree.commons.data.resource.writeTo
import java.io.InputStream

class CacheResourceLocation(
	private val sourceLocation: ResourceLocation,
	private val cacheLocation: MutableResourceLocation,
) : ResourceLocation {

	override fun getResourceOrNull(name: String): FileResource? {
		val source = sourceLocation.getFileResourceOrNull(name) ?: return null
		val cache = cacheLocation.getFileResource(name)
		return CacheFileResource(source, cache)
	}

	override fun getFileResourceOrNull(name: String): FileResource? {
		val source = sourceLocation.getFileResourceOrNull(name) ?: return null
		val cache = cacheLocation.getFileResource(name)
		return CacheFileResource(source, cache)
	}

	data class CacheFileResource(private val source: FileResource, private val cache: MutableFileResource) :
		FileResource {

		override fun exists() = source.exists()

		override val length: Long
			get() {
				update()
				return source.length
			}

		override fun open(): InputStream {
			update()
			return cache.open()
		}

		override val parent: ParentResource
			get() = TODO("Not yet implemented")
		override val name: String
			get() = source.name

		override fun lastModified() = source.lastModified()

		private fun update() {
			source.writeTo(cache, cache.lastModified())
		}
	}
}