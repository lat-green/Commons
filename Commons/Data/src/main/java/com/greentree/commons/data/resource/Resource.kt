package com.greentree.commons.data.resource

import java.io.InputStream
import java.io.Serializable
import java.net.MalformedURLException
import java.net.URL

interface Resource : Serializable {

	/** @return Action or [NullResourceAction.NULL_RESOURCE_ACTION]
	 */
	val action: ResourceAction
		get() = ResourceAction.Null
	val name: String

	/** @return length or -1 if unknown
	 */
	fun length(): Long

	fun lastModified(): Long

	fun open(): InputStream

	@Throws(MalformedURLException::class)
	fun url(): URL
	fun exists(): Boolean

	object Null : Resource {

		override val action: ResourceAction
			get() = ResourceAction.Null
		override val name: String
			get() = throw UnsupportedOperationException()

		override fun length(): Long {
			throw UnsupportedOperationException()
		}

		override fun lastModified(): Long {
			throw UnsupportedOperationException()
		}

		override fun open(): InputStream {
			throw UnsupportedOperationException()
		}

		override fun url(): URL {
			throw UnsupportedOperationException()
		}

		override fun exists() = false
	}
}

fun Resource.writeTo(result: IOResource, lastRead: Long) {
	val m = lastModified()
	if(m == 0L || m > lastRead) writeTo(result)
}

fun Resource.writeTo(result: IOResource) {
	result.openWrite().use { out -> open().use { `in` -> `in`.transferTo(out) } }
}