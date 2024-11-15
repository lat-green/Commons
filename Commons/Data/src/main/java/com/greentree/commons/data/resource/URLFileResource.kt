package com.greentree.commons.data.resource

import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

data class URLFileResource(
	val url: URL,
) : MutableFileResource {

	override fun createFile(): Boolean {
		TODO("Not yet implemented")
	}

	override fun openWrite(): OutputStream {
		TODO("Not yet implemented")
	}

	override val length: Long
		get() {
			val connection = url.openConnection()
			if(connection is HttpURLConnection)
				connection.requestMethod = "HEAD"
			return connection.contentLengthLong
		}

	override fun open(): InputStream {
		val connection = url.openConnection()
		return connection.getInputStream()
	}

	override fun exists() = true

	override val parent: ParentResource
		get() = TODO("Not yet implemented")
	override val name: String
		get() = TODO("Not yet implemented")

	override fun lastModified(): Long {
		val connection = url.openConnection()
		if(connection is HttpURLConnection)
			connection.requestMethod = "HEAD"
		return connection.lastModified
	}

	override fun delete(): Boolean {
		TODO("Not yet implemented")
	}
}